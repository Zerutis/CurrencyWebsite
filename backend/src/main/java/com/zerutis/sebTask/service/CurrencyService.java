package com.zerutis.sebTask.service;

import com.zerutis.sebTask.XmlHandler;
import com.zerutis.sebTask.dao.CurrencyRepo;
import com.zerutis.sebTask.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepo currencyRepo;

    @Autowired
    XmlHandler xmlHandler;

    public Currency addCurrency(Currency currency) {
        currencyRepo.save(currency);
        return currency;
    }

    @Scheduled(cron = "0 0 0 */1 * *")
    public void UpdateRatesDaily(){
        xmlHandler.updateCurrency(currencyRepo.findAll());
    }

    public Currency updateCurrency(Currency currency, String code) {
        Currency temp = currencyRepo.findByCode(code).orElse(null);
        if(temp == null){
            return null;
        }
        System.out.println("Updated");
        temp.setName(currency.getName());
        temp.setCode(currency.getCode());
        temp.setFxRate(currency.getFxRate());
        currencyRepo.save(temp);
        return temp;
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
