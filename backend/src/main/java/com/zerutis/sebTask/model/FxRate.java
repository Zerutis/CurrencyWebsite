package com.zerutis.sebTask.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name="FxRate")
@Table(name="fxRate")
public class FxRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fxRate_id")
    private Integer id;

    @OneToOne
    @JoinColumn(name="code")
    @JsonBackReference(value = "currency")
    private Currency currency;


    @Column(name = "value", precision = 12, scale = 5)
    private BigDecimal value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FxRate{" +
                "id=" + id +
                ", curr=" + currency +
                ", value=" + value +
                '}';
    }
}
