package com.zerutis.sebTask.controller;

import com.zerutis.sebTask.exception.ApiRequestException;
import com.zerutis.sebTask.model.RateHistory;
import com.zerutis.sebTask.service.RateHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RateHistoryController {
    @Autowired
    RateHistoryService rateHistoryService;

    @PostMapping("/rate-history/{currency_code}")
    public RateHistory addRateHistory(
            @RequestBody RateHistory rateHistory,
            @PathVariable("currency_code") String currencyCode)
    {
        return rateHistoryService.addRateHistory(rateHistory, currencyCode.toUpperCase());
    }

    @PutMapping("/rate-history/{currencyCode}")
    public RateHistory saveOrUpdateRateHistory(
            @RequestBody RateHistory rateHistory,
            @PathVariable("currencyCode") String currencyCode)
    {
        return rateHistoryService.addRateHistory(rateHistory,currencyCode.toUpperCase());
    }

    @DeleteMapping("/rate-history/{id}")
    public String deleteRateHistory(@PathVariable("id") int id)
    {
        return rateHistoryService.deleteRateHistory(id);
    }

    @GetMapping("/rate-histories")
    public List<RateHistory> getFxRates()
    {
        List<RateHistory> rateHistories = rateHistoryService.getRateHistories();
        if (rateHistories.isEmpty()) {
            throw new ApiRequestException("There is no rate history");
        }
        return rateHistories;
    }

    @GetMapping("/rate-history/{id}")
    public Optional<RateHistory> getFxRate(@PathVariable("id") int id)
    {
        Optional<RateHistory> rateHistory = rateHistoryService.getRateHistoryById(id);
        if (rateHistory.isEmpty()) {
            throw new ApiRequestException("There is no rate history by ID: " + id);
        }

        return rateHistory;
    }
}
