package com.zerutis.sebTask.service;

import com.zerutis.sebTask.dao.CurrencyRepo;
import com.zerutis.sebTask.model.Currency;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyRepoTest {
    @Autowired
    private CurrencyService currencyService;

    @MockBean
    private CurrencyRepo repository;

    @Test
    public void getUsersTest() {
        when(repository.findAll()).thenReturn(Stream
                .of(new Currency(376, "USD", "JAV doleris", new BigDecimal( .217)), new Currency(958, "EUR", "Euras", new BigDecimal(1))).collect(Collectors.toList()));
        assertEquals(2, currencyService.getCurrencies().size());
    }

    @Test
    public void getCurrencyByCode() {
        String code = "EUR";
        Currency curr = new Currency(958, "EUR", "Euras", new BigDecimal(1));
        when(repository.findByCode(code)).thenReturn(java.util.Optional.of(curr));
        assertEquals(curr, currencyService.getCurrencyByCode(code).get());
    }

    @Test
    public void saveUserTest() {
        Currency currency = new Currency(1, "LTL", "Lietuvos Litas", new BigDecimal(3.5357));
        when(repository.save(currency)).thenReturn(currency);
        assertEquals(currency, currencyService.addCurrency(currency));
    }
}
