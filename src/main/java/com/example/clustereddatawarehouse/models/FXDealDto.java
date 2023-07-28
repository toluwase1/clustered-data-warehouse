package com.example.clustereddatawarehouse.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FXDealDto(
        String dealUniqueId,
        String fromCurrency,
        String toCurrency,
        LocalDateTime dealTimestamp,
        BigDecimal dealAmount
){}
