package com.example.clustereddatawarehouse.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
public class FXDeal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String dealUniqueId;
    private String fromCurrency;
    private String toCurrency;
    private LocalDateTime dealTimestamp;
    private BigDecimal dealAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FXDeal fxDeal = (FXDeal) o;
        return Objects.equals(id, fxDeal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
//dockerize
//testing
//Questions
