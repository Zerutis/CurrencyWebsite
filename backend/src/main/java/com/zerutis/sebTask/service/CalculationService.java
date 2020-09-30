package com.zerutis.sebTask.service;

import com.zerutis.sebTask.dao.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculationService
{
    @Autowired
    CurrencyRepo currencyRepo;

    private BigDecimal calculateValue(BigDecimal fxRate, BigDecimal value) {
        return value.multiply(fxRate);
    }

    public BigDecimal getValue(String currencyCode, BigDecimal value){
        BigDecimal rate = currencyRepo.findRateByCode(currencyCode).orElse(new BigDecimal(0));

        return calculateValue(rate, value);
    }
}
