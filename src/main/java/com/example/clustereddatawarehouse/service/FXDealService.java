package com.example.clustereddatawarehouse.service;

import com.example.clustereddatawarehouse.entity.FXDeal;
import com.example.clustereddatawarehouse.models.Errors;
import com.example.clustereddatawarehouse.models.FXDealDto;
import com.example.clustereddatawarehouse.repository.FXDealRepository;
import com.example.clustereddatawarehouse.models.CustomResponse;
import jakarta.annotation.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Service
public class FXDealService {
    @Autowired
    FXDealRepository fxDealRepository;

    public CustomResponse<Object> importFXDeals(FXDealDto deal) {

        var validationResult = validateDeal(deal);
        if (!validationResult.isEmpty()) {
            return new CustomResponse<>(false, true,validationResult , HttpStatus.BAD_REQUEST.value());
        }

        if (fxDealRepository.existsByDealUniqueId(deal.dealUniqueId())) {
            return new CustomResponse<>(false, true, "Deal with ID already exists: " + deal.dealUniqueId(), HttpStatus.CONFLICT.value());
        }
        FXDeal fxDeal = new FXDeal();
        BeanUtils.copyProperties(deal, fxDeal);
        fxDealRepository.save(fxDeal);

        return new CustomResponse<>(true, false, deal, HttpStatus.CREATED.value());
    }


    private List<Errors>  validateDeal(FXDealDto deal) {

        List<Errors> list = new ArrayList<>();


        if (deal.dealUniqueId() == null || deal.dealUniqueId().trim().isEmpty()) {
            list.add(new Errors("dealUniqueID",deal.dealUniqueId()));
        }

        if (!isValidCurrency(deal.fromCurrency())) {
            list.add(new Errors("fromCurrency",deal.fromCurrency()));
        }

        if (!isValidCurrency(deal.toCurrency())) {
            list.add(new Errors("toCurrency",deal.toCurrency()));
        }

        if (deal.dealTimestamp() == null || (deal.dealTimestamp().isAfter(LocalDateTime.now()))) {
            list.add(new Errors("dealTimeStamp",deal.dealTimestamp()));
        }

        if (deal.dealAmount() == null || deal.dealAmount().compareTo(BigDecimal.ZERO) <= 0) {
            list.add(new Errors("dealAmount",deal.dealAmount()));
        }

        return list;

    }
    private boolean isValidCurrency(@Nullable String currency) {
        return currency != null && Currency.getAvailableCurrencies().stream().
                map(Currency::getCurrencyCode).anyMatch(code -> code.equals(currency.trim()));
    }
}

