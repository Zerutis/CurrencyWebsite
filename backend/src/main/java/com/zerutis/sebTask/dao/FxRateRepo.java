package com.zerutis.sebTask.dao;

import com.zerutis.sebTask.model.FxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface FxRateRepo extends JpaRepository<FxRate,Integer> {

    @Query(value = "SELECT f.value FROM Fx_Rate f, Currency c " +
            "WHERE c.code = :code " +
            "AND c.currency_id = f.code", nativeQuery = true)
    Optional<BigDecimal> findByCode(@Param("code") String currencyCode);
}
