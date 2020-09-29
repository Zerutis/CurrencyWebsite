package com.zerutis.sebTask.controller;

import com.zerutis.sebTask.exception.ApiRequestException;
import com.zerutis.sebTask.model.Currency;
import com.zerutis.sebTask.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @PostMapping(path = "/currency", consumes = "application/json", produces = "application/json")
    public Currency addCurrency(@RequestBody Currency currency)
    {
        return currencyService.addCurrency(currency);
    }

    @PutMapping("/currency")
    public Currency saveOrUpdateCurrency(@RequestBody Currency currency) { return currencyService.addCurrency(currency); }

    @DeleteMapping("/currency/{id}")
    public String deleteCurrency(@PathVariable("id") int id)
    {
        return currencyService.deleteCurrency(id);
    }

    @GetMapping("/currencies")
    public List<Currency> getCurrencies()
    {
        List<Currency> currencies = currencyService.getCurrencies();
        if (currencies.isEmpty()) {
            throw new ApiRequestException("There is no currencies");
        }
        return currencies;
    }

    @GetMapping("/currency/{id}")
    public Optional<Currency> getOwner(@PathVariable("id") int id)
    {
        Optional<Currency> currency = currencyService.getCurrencyById(id);
        if (currency.isEmpty()) {
            throw new ApiRequestException("There is no currency by ID: " + id);
        }

        return currency;
    }
}
