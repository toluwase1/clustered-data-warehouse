package com.example.clustereddatawarehouse.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

}
//code structly and style, improvements
//dockerize
//testing
