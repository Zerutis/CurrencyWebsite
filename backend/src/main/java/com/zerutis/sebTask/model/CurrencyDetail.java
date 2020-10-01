package com.zerutis.sebTask.model;

import java.math.BigDecimal;

public class CurrencyDetail {
    private String date;
    private BigDecimal fxRate;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getFxRate() {
        return fxRate;
    }

    public void setFxRate(BigDecimal fxRate) {
        this.fxRate = fxRate;
    }

    @Override
    public String toString() {
        return "CurrencyDetail{" +
                ", date='" + date + '\'' +
                ", fxRate=" + fxRate +
                '}';
    }
}
