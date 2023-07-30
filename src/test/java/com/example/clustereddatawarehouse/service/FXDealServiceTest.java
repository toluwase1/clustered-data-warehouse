package com.example.clustereddatawarehouse.service;

import com.example.clustereddatawarehouse.models.Errors;
import com.example.clustereddatawarehouse.models.FXDealDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FXDealServiceTest {

    private FXDealService fxDealService;

    @BeforeEach
    public void setUp() {
        fxDealService = new FXDealService();
    }

    @Test
    public void testValidDeal() {
        FXDealDto deal = new FXDealDto("FXD12345", "USD", "EUR", LocalDateTime.now().minusDays(1), BigDecimal.valueOf(1000.0));
        List<Errors> errorsList = fxDealService.validateDeal(deal);
        assertTrue(errorsList.isEmpty(), "No errors should be present for a valid deal");
    }

    @Test
    public void testInvalidDealUniqueId() {
        FXDealDto deal = new FXDealDto(null, "USD", "EUR", LocalDateTime.now().minusDays(1), BigDecimal.valueOf(1000.0));
        List<Errors> errorsList = fxDealService.validateDeal(deal);
        assertEquals(1, errorsList.size(), "One error should be present for missing dealUniqueId");
        assertEquals("dealUniqueID", errorsList.get(0).field(), "Error should be for dealUniqueId field");
    }

    @Test
    public void testInvalidFromCurrency() {
        FXDealDto deal = new FXDealDto("FXD12345", "USDD", "EUR", LocalDateTime.now().minusDays(1), BigDecimal.valueOf(1000.0));
        List<Errors> errorsList = fxDealService.validateDeal(deal);
        assertEquals(1, errorsList.size(), "One error should be present for invalid fromCurrency");
        assertEquals("fromCurrency", errorsList.get(0).field(), "Error should be for fromCurrency field");
    }

    @Test
    public void testInvalidToCurrency() {
        FXDealDto deal = new FXDealDto("FXD12345", "USD", "EURR", LocalDateTime.now().minusDays(1), BigDecimal.valueOf(1000.0));
        List<Errors> errorsList = fxDealService.validateDeal(deal);
        assertEquals(1, errorsList.size(), "One error should be present for invalid toCurrency");
        assertEquals("toCurrency", errorsList.get(0).field(), "Error should be for toCurrency field");
    }

    @Test
    public void testInvalidDealTimestamp() {
        FXDealDto deal = new FXDealDto("FXD12345", "USD", "EUR", LocalDateTime.now().plusDays(1), BigDecimal.valueOf(1000.0));
        List<Errors> errorsList = fxDealService.validateDeal(deal);
        assertEquals(1, errorsList.size(), "One error should be present for future dealTimestamp");
        assertEquals("dealTimeStamp", errorsList.get(0).field(), "Error should be for dealTimestamp field");
    }

    @Test
    public void testInvalidDealAmount() {
        FXDealDto deal = new FXDealDto("FXD12345", "USD", "EUR", LocalDateTime.now().minusDays(1), BigDecimal.valueOf(-1000.0));
        List<Errors> errorsList = fxDealService.validateDeal(deal);
        assertEquals(1, errorsList.size(), "One error should be present for invalid dealAmount");
        assertEquals("dealAmount", errorsList.get(0).field(), "Error should be for dealAmount field");
    }
}

