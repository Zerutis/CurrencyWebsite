package com.zerutis.sebTask.controller;

import com.zerutis.sebTask.XmlHandler;
import com.zerutis.sebTask.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class WebAppController {
    @Autowired
    CalculationService calculationService;


    @GetMapping("/fx-rate/{code}/{value}")
    public BigDecimal getCalculatedSum(
            @PathVariable String code,
            @PathVariable BigDecimal value
    ){
        return calculationService.getValue(code,value);
    }
}
