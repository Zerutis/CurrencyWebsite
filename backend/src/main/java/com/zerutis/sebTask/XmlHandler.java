package com.zerutis.sebTask;


import com.zerutis.sebTask.model.Currency;
import com.zerutis.sebTask.model.CurrencyDetail;
import com.zerutis.sebTask.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RestController
public class XmlHandler {
    private final String currencyURL = "http://www.lb.lt/webservices/FxRates/FxRates.asmx/getCurrencyList";
    private final String fxRateURL = "http://www.lb.lt/webservices/FxRates/FxRates.asmx/getCurrentFxRates?tp=eu";

    private Document currencyDoc = setDocument(currencyURL);
    private Document fxRateDoc = setDocument(fxRateURL);
    private Document currencyDetailDoc;

    XmlHandler(){}

    public XmlHandler(String code, String dtFrom, String dtTo){
        currencyDetailDoc = setDocument("http://www.lb.lt/webservices/FxRates/FxRates.asmx/getFxRatesForCurrency?tp=eu&ccy="
                + code +"&dtFrom="+ dtFrom + "&dtTo=" + dtTo);
    }

    @Autowired
    CurrencyService currencyService;

    private static Document setDocument(String docURL) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            factory.setIgnoringComments(true);

            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            return documentBuilder.parse(docURL);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void fillCurrency() {
        NodeList currencyList = currencyDoc.getElementsByTagName("CcyNtry");
        NodeList fxRateList = fxRateDoc.getElementsByTagName("FxRate");

        setCurrency(currencyList, fxRateList, currencyService);
    }

    private static void setCurrency(NodeList currencyList, NodeList fxRateList, CurrencyService currencyService) {
        try {
            List<Currency> currencies = new ArrayList<>();
            for (int i = 0; i < currencyList.getLength(); i++) {

                Node currency = currencyList.item(i);
                if (currency.getNodeType() == Node.ELEMENT_NODE) {
                    Element currElement = (Element) currency;

                    NodeList codeList = currElement.getElementsByTagName("Ccy").item(0).getChildNodes();
                    NodeList nameList = currElement.getElementsByTagName("CcyNm").item(0).getChildNodes();

                    Currency curr = new Currency();
                    curr.setCode(((Node) codeList.item(0)).getNodeValue().trim());
                    curr.setName(((Node) nameList.item(0)).getNodeValue().trim());

                    currencies.add(curr);
                }
            }
            for (int i = 0; i < fxRateList.getLength(); i++) {

                Node fxRate = fxRateList.item(i);
                if (fxRate.getNodeType() == Node.ELEMENT_NODE) {
                    Element fxRateElement = (Element) fxRate;
                    Node CcyAmt = fxRateElement.getElementsByTagName("CcyAmt").item(1);

                    if (CcyAmt.getNodeType() == Node.ELEMENT_NODE) {
                        Element CcyAmtElement = (Element) CcyAmt;

                        String currencyCode = CcyAmtElement.getElementsByTagName("Ccy").item(0).getTextContent();
                        BigDecimal currencyFxRate = new BigDecimal(CcyAmtElement.getElementsByTagName("Amt").item(0).getTextContent());

                        currencies.forEach(currency -> {
                            if (currencyCode.equals(currency.getCode())) {
                                currency.setFxRate(currencyFxRate);
                                return;
                            }
                        });
                    }
                }
            }
            currencies.forEach(currency -> currencyService.addCurrency(currency));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateCurrency(List<Currency> currencies) {
        setUpdatedCurrency(currencies, currencyService, fxRateDoc);
    }

    private static void setUpdatedCurrency(List<Currency> currencies, CurrencyService service, Document doc){
        try {
            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList codeList = (NodeList) xp.compile("/FxRates/FxRate/CcyAmt[2]/Ccy/text()").evaluate(doc, XPathConstants.NODESET);
            NodeList rateList = (NodeList) xp.compile("/FxRates/FxRate/CcyAmt[2]/Amt/text()").evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < codeList.getLength(); i++){
                String code = codeList.item(i).getNodeValue();
                BigDecimal rate = new BigDecimal(rateList.item(i).getNodeValue());
                currencies.forEach(currency -> {
                    if(code.equals(currency.getCode()))
                        currency.setFxRate(rate);
                });
            }
            currencies.forEach(currency -> service.updateCurrency(currency,currency.getCode()));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public List<CurrencyDetail> getCurrencyDetail(){
        try {
            List<CurrencyDetail> details = new ArrayList<>();
            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList rateList = (NodeList) xp.compile("/FxRates/FxRate/CcyAmt[2]/Amt/text()").evaluate(currencyDetailDoc,
                    XPathConstants.NODESET);
            NodeList dateList = (NodeList) xp.compile("/FxRates/FxRate/Dt/text()").evaluate(currencyDetailDoc,
                    XPathConstants.NODESET);

            for (int i = 0; i < rateList.getLength(); i++) {
                CurrencyDetail currencyDetail = new CurrencyDetail();
                String date = dateList.item(i).getNodeValue();
                BigDecimal rate = new BigDecimal(rateList.item(i).getNodeValue());
                currencyDetail.setDate(date);
                currencyDetail.setFxRate(rate);
                details.add(currencyDetail);
            }
            System.out.println(details.size());
            return details;
        }catch (Exception e){
            return null;
        }
    }
}
