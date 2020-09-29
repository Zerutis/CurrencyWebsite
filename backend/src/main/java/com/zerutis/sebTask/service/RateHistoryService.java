package com.zerutis.sebTask.service;

import com.zerutis.sebTask.dao.CurrencyRepo;
import com.zerutis.sebTask.dao.RateHistoryRepo;
import com.zerutis.sebTask.model.Currency;
import com.zerutis.sebTask.model.RateHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RateHistoryService {
    @Autowired
    RateHistoryRepo rateHistoryRepo;

    @Autowired
    CurrencyRepo currencyRepo;

    public RateHistory addRateHistory(RateHistory rateHistory, String currencyCode){
        Currency currency = currencyRepo.findByCode(currencyCode).orElse(new Currency());
        rateHistory.setCurrency(currency);
        rateHistoryRepo.save(rateHistory);
        return rateHistory;
    }

    public String deleteRateHistory(int id) {
        rateHistoryRepo.delete(rateHistoryRepo.getOne(id));
        return "RateHistory deleted";
    }

    public List<RateHistory> getRateHistories(){
        return rateHistoryRepo.findAll();
    }

    public Optional<RateHistory> getRateHistoryById(int id) { return rateHistoryRepo.findById(id); }
}
