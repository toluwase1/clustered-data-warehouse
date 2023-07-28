package com.example.clustereddatawarehouse.service;

import com.example.clustereddatawarehouse.models.FXDeal;
import com.example.clustereddatawarehouse.repository.FXDealRepository;
import com.example.clustereddatawarehouse.response.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class FXDealService {
    @Autowired
    FXDealRepository fxDealRepository;

    public CustomResponse<String> importFXDeals(FXDeal deal) {

        CustomResponse<String> validationResult = validateDeal(deal);
        if (validationResult.isError()) {
            return validationResult;
        }

        if (fxDealRepository.existsByDealUniqueId(deal.getDealUniqueId())) {
            return new CustomResponse<>(false, true, "Deal with ID already exists: " + deal.getDealUniqueId(), HttpStatus.CONFLICT.value());
        }

        fxDealRepository.save(deal);

        return new CustomResponse<>(true, false, "Deal imported successfully.", HttpStatus.OK.value());
    }
    public class InvalidDealException extends RuntimeException {
        public InvalidDealException(String message) {
            super(message);
        }
    }
    public class DuplicateDealException extends RuntimeException {
        public DuplicateDealException(String message) {
            super(message);
        }
    }

    private CustomResponse<String> validateDeal(FXDeal deal) {

        deal.setDealUniqueId(deal.getDealUniqueId().trim());
        deal.setFromCurrency(deal.getFromCurrency().trim());
        deal.setToCurrency(deal.getToCurrency().trim());

        if (deal.getDealUniqueId() == null || deal.getDealUniqueId().trim().isEmpty()) {
            return new CustomResponse<>(false, true, "Deal Unique ID is missing.", HttpStatus.BAD_REQUEST.value());
        }

        if (deal.getFromCurrency() == null || deal.getFromCurrency().trim().isEmpty()) {
            return new CustomResponse<>(false, true, "From Currency ISO Code is missing.", HttpStatus.BAD_REQUEST.value());
        }

        if (deal.getToCurrency() == null || deal.getToCurrency().trim().isEmpty()) {
            return new CustomResponse<>(false, true, "To Currency ISO Code is missing.", HttpStatus.BAD_REQUEST.value());
        }

        if (deal.getDealTimestamp() == null) {
            return new CustomResponse<>(false, true, "Deal Timestamp is missing.", HttpStatus.BAD_REQUEST.value());
        }

        String dealTimestampString = String.valueOf(deal.getDealTimestamp()); // Assuming you have a getter for the dealTimestamp string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        try {
            LocalDateTime dealTimestamp = LocalDateTime.parse(dealTimestampString, formatter);
        } catch (DateTimeParseException e) {
            return new CustomResponse<>(false, true, "Malformed Deal Timestamp. Please use the format 'yyyy-MM-ddTHH:mm:ss'.", HttpStatus.BAD_REQUEST.value());
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        if (deal.getDealTimestamp().isAfter(currentDateTime)) {
            return new CustomResponse<>(false, true, "Invalid Deal Timestamp.", HttpStatus.BAD_REQUEST.value());
        }

        if (deal.getDealAmount() == null || deal.getDealAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return new CustomResponse<>(false, true, "Invalid Deal Amount.", HttpStatus.BAD_REQUEST.value());
        }

        return new CustomResponse<>(true, false, "Deal is valid.", HttpStatus.CREATED.value());
    }

}
