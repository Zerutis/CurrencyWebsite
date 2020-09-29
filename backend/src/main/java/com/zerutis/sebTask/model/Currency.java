package com.zerutis.sebTask.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="Currency")
@Table(name="Currency")
public class Currency {
    @Column(name = "currency_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @JsonManagedReference(value = "currency")
    @OneToOne(mappedBy = "currency", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private FxRate fxRate;

    @JsonManagedReference(value = "currency")
    @OneToMany(mappedBy = "currency", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RateHistory> rateHistories = new ArrayList<>();

    public Integer getId() {
        return id;
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

    public FxRate getFxRate() {
        return fxRate;
    }

    public void setFxRate(FxRate fxRate) {
        this.fxRate = fxRate;
    }

    public List<RateHistory> getRateHistories() {
        return rateHistories;
    }

    public void setRateHistories(List<RateHistory> rateHistories) {
        this.rateHistories = rateHistories;
    }
}
