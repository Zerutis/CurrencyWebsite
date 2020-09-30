package com.zerutis.sebTask;


import com.zerutis.sebTask.model.Currency;
import com.zerutis.sebTask.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class XmlHandler {
    private Document currencyDoc = setDocument("http://www.lb.lt/webservices/FxRates/FxRates.asmx/getCurrencyList");
    private Document fxRateDoc = setDocument("http://www.lb.lt/webservices/FxRates/FxRates.asmx/getCurrentFxRates?tp=eu");

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
}
