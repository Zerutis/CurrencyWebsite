package com.zerutis.sebTask.controller;

import com.zerutis.sebTask.XmlHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebAppController {
    @Autowired
    XmlHandler xmlHandler;

    @GetMapping
    public void addCurrencies(){
        xmlHandler.fillCurrency();
    }
}
