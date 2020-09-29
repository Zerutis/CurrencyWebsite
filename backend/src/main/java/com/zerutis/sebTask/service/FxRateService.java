package com.zerutis.sebTask.service;

import com.zerutis.sebTask.dao.CurrencyRepo;
import com.zerutis.sebTask.dao.FxRateRepo;
import com.zerutis.sebTask.model.Currency;
import com.zerutis.sebTask.model.FxRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FxRateService {
    @Autowired
    FxRateRepo fxRateRepo;

    @Autowired
    CurrencyRepo currencyRepo;

    public FxRate addFxRate(FxRate fxRate, String currencyCode){
        Currency currency = currencyRepo.findByCode(currencyCode).orElse(new Currency());
        fxRate.setCurrency(currency);
        fxRateRepo.save(fxRate);
        return fxRate;
    }

    public String deleteFxRate(int id) {
        fxRateRepo.delete(fxRateRepo.getOne(id));
        return "FxRate deleted";
    }

    public List<FxRate> getFxRates(){
        return fxRateRepo.findAll();
    }

    public Optional<FxRate> getFxRateById(int id) { return  fxRateRepo.findById(id); }
}
