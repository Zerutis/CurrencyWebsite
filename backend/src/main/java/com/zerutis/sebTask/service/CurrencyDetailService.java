package com.zerutis.sebTask.service;

import com.zerutis.sebTask.XmlHandler;
import com.zerutis.sebTask.model.CurrencyDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyDetailService {


    public List<CurrencyDetail> getCurrencyDetail(String code, String dtFrom, String dtTo){
        XmlHandler xmlHandler = new XmlHandler(code, dtFrom, dtTo);

        return xmlHandler.getCurrencyDetail();
    }
}
