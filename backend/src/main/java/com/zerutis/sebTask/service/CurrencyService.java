package com.zerutis.sebTask.service;

import com.zerutis.sebTask.dao.CurrencyRepo;
import com.zerutis.sebTask.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepo currencyRepo;

    public Currency addCurrency(Currency currency) {
        currencyRepo.save(currency);
        return currency;
    }

    public Currency updateCurrency(Currency currency) {
        return currency;
    }

    public String deleteCurrency(int id) {
        currencyRepo.delete(currencyRepo.getOne(id));
        return "Currency deleted";
    }

    public List<Currency> getCurrencies(){
        return currencyRepo.findAll();
    }

    public Optional<Currency> getCurrencyById(int id) { return  currencyRepo.findById(id); }
}
