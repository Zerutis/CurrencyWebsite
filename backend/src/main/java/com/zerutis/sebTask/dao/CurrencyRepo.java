package com.zerutis.sebTask.dao;

import com.zerutis.sebTask.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface CurrencyRepo extends JpaRepository<Currency,Integer> {

    @Query(value = "SELECT c.fx_rate FROM Currency c " +
            "WHERE c.code = :code", nativeQuery = true)
    Optional<BigDecimal> findByCode(@Param("code") String currencyCode);
}
