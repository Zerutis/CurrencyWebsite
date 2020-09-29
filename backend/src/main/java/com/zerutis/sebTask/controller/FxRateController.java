package com.zerutis.sebTask.controller;

import com.zerutis.sebTask.XmlHandler;
import com.zerutis.sebTask.exception.ApiRequestException;
import com.zerutis.sebTask.model.FxRate;
import com.zerutis.sebTask.service.FxRateService;
import com.zerutis.sebTask.service.ValueCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
public class FxRateController {

    @Autowired
    FxRateService fxRateService;

    @Autowired
    ValueCalculationService calculationService;

    @PostMapping("/fx-rate/{currency_code}")
    public FxRate addFxRate(
            @RequestBody FxRate fxRate,
            @PathVariable("currency_code") String currencyCode)
    {
        return fxRateService.addFxRate(fxRate, currencyCode.toUpperCase());
    }

    @PutMapping("/fx-rate/{currencyCode}")
    public FxRate saveOrUpdateFxRate(
            @RequestBody FxRate fxRate,
            @PathVariable("currencyCode") String currencyCode)
    {
        return fxRateService.addFxRate(fxRate,currencyCode.toUpperCase());
    }

    @DeleteMapping("/fx-rate/{id}")
    public String deleteFxRate(@PathVariable("id") int id)
    {
        return fxRateService.deleteFxRate(id);
    }

    @GetMapping("/fx-rates")
    public List<FxRate> getFxRates()
    {
        List<FxRate> fxRates = fxRateService.getFxRates();
        if (fxRates.isEmpty()) {
            throw new ApiRequestException("There is no exchange rates");
        }
        return fxRates;
    }

    @GetMapping("/fx-rate/{id}")
    public Optional<FxRate> getFxRate(@PathVariable("id") int id)
    {
        Optional<FxRate> fxRate = fxRateService.getFxRateById(id);
        if (fxRate.isEmpty()) {
            throw new ApiRequestException("There is no exchange rate by ID: " + id);
        }

        return fxRate;
    }

    @GetMapping("/fx-rate/{code}/{sum}")
    public BigDecimal getSum(
            @PathVariable("code") String code,
            @PathVariable("sum") BigDecimal sum){
        return calculationService.getValue(code, sum);
    }

}
