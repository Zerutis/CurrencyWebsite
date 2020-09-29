package com.zerutis.sebTask.dao;

import com.zerutis.sebTask.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepo extends JpaRepository<Currency,Integer> {
    Optional<Currency> findByCode(String currencyCode);
}