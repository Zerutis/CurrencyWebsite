package com.zerutis.sebTask;


import com.zerutis.sebTask.model.Currency;
import com.zerutis.sebTask.model.FxRate;
import com.zerutis.sebTask.model.RateHistory;
import com.zerutis.sebTask.service.CurrencyService;
import com.zerutis.sebTask.service.FxRateService;
import com.zerutis.sebTask.service.RateHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.zip.DataFormatException;

@RestController
public class XmlHandler {
    public Document currencyDoc = setDocument("http://www.lb.lt/webservices/FxRates/FxRates.asmx/getCurrencyList");
    public Document fxRateDoc = setDocument("http://www.lb.lt/webservices/FxRates/FxRates.asmx/getCurrentFxRates?tp=eu");
    public Document rateHistoryDoc = setDocument("http://www.lb.lt/webservices/FxRates/FxRates.asmx/getFxRatesForCurrency?tp=eu&ccy=usd&dtFrom=2020-03-01&dtTo=2020-04-01");

    @Autowired
    CurrencyService currencyService;

    @Autowired
    FxRateService fxRateService;

    @Autowired
    RateHistoryService rateHistoryService;

    private static Document setDocument(String docURL){
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

    public void fillCurrency(){
        NodeList currencyList = currencyDoc.getElementsByTagName("CcyNtry");

        setCurrency(currencyList, currencyService);
    }
    private static void setCurrency(NodeList listOfCurrency, CurrencyService currencyService)
    {
        try {
            for(int i=0; i < listOfCurrency.getLength(); i++) {

                Node currency = listOfCurrency.item(i);
                if (currency.getNodeType() == Node.ELEMENT_NODE) {
                    Element currElement = (Element) currency;

                    NodeList codeList = currElement.getElementsByTagName("Ccy").item(0).getChildNodes();
                    NodeList nameList = currElement.getElementsByTagName("CcyNm").item(0).getChildNodes();

                    Currency curr = new Currency();
                    curr.setCode(((Node) codeList.item(0)).getNodeValue().trim());
                    curr.setName(((Node) nameList.item(0)).getNodeValue().trim());

                    currencyService.addCurrency(curr);
                }
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void fillFxRate(){
        NodeList fxRateList = fxRateDoc.getElementsByTagName("FxRate");

        setFxRate(fxRateList, fxRateService);
    }

    private static void setFxRate(NodeList fxRateList, FxRateService fxRateService){
        try {
            for(int i=0; i < fxRateList.getLength(); i++) {

                Node rate = fxRateList.item(i);
                if (rate.getNodeType() == Node.ELEMENT_NODE) {
                    Element rateElement = (Element) rate;
                    Node CcyAmt = rateElement.getElementsByTagName("CcyAmt").item(1);

                    if (CcyAmt.getNodeType() == Node.ELEMENT_NODE) {
                        Element CcyAmtElement = (Element) CcyAmt;

                        String currencyCode = CcyAmtElement.getElementsByTagName("Ccy").item(0).getTextContent();
                        BigDecimal currencyFxRate = new BigDecimal(CcyAmtElement.getElementsByTagName("Amt").item(0).getTextContent());

                        FxRate fxRate = new FxRate();
                        fxRate.setValue(currencyFxRate);
                        fxRateService.addFxRate(fxRate, currencyCode);
                    }
                }
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void fillRateHistory(){
        NodeList rateHisList = rateHistoryDoc.getElementsByTagName("FxRate");

        setRateHistory(rateHisList, rateHistoryService);
    }

    private static void setRateHistory(NodeList rateHisList, RateHistoryService rateHisService){
        try {
            for(int i=0; i < rateHisList.getLength(); i++) {

                Node rate = rateHisList.item(i);
                if (rate.getNodeType() == Node.ELEMENT_NODE) {
                    Element rateElement = (Element) rate;
                    String date = rateElement.getElementsByTagName("Dt").item(0).getTextContent();
                    Node CcyAmt = rateElement.getElementsByTagName("CcyAmt").item(1);

                    if (CcyAmt.getNodeType() == Node.ELEMENT_NODE) {
                        Element CcyAmtElement = (Element) CcyAmt;

                        BigDecimal currencyFxRate = new BigDecimal(CcyAmtElement.getElementsByTagName("Amt").item(0).getTextContent());

                        RateHistory rateHistory = new RateHistory();
                        rateHistory.setDate(date);
                        rateHistory.setValue(currencyFxRate);
                        rateHisService.addRateHistory(rateHistory, "USD");
                    }
                }
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
