package com.zerutis.sebTask.service;

import com.zerutis.sebTask.dao.FxRateRepo;
import com.zerutis.sebTask.model.FxRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ValueCalculationService
{
    @Autowired
    FxRateRepo fxRateRepo;

    private BigDecimal calculateValue(BigDecimal fxRate, BigDecimal value) {
        return value.multiply(fxRate);
    }

    public BigDecimal getValue(String currencyCode, BigDecimal value){
        BigDecimal fxRate = fxRateRepo.findByCode(currencyCode).orElse(new BigDecimal(0));

        return calculateValue(fxRate, value);
    }
}
