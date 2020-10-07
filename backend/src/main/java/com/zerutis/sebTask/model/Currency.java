package com.zerutis.sebTask.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "Currency")
@Entity(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    private Integer id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "fxRate", precision = 12, scale = 5)
    private BigDecimal fxRate;

    public Integer getId() {
        return id;
    }

    public Currency(){}

    public Currency(Integer id, String code, String name, BigDecimal fxRate) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.fxRate = fxRate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getFxRate() {
        return fxRate;
    }

    public void setFxRate(BigDecimal fxRate) {
        this.fxRate = fxRate;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", fxRate=" + fxRate +
                '}';
    }
}
