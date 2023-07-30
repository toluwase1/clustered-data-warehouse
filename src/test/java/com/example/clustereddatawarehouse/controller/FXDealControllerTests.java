package com.example.clustereddatawarehouse.controller;

import com.example.clustereddatawarehouse.models.CustomResponse;
import com.example.clustereddatawarehouse.models.FXDealDto;
import com.example.clustereddatawarehouse.service.FXDealService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FXDealController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class FXDealControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FXDealService fxDealService;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    public void FXDealController_ImportFxDeals_ReturnCreated() throws Exception {
//        given(fxDealService.importFXDeals(ArgumentMatchers.any())).will
//    }


    @Test
    public void testImportFXDeals() throws Exception {
        FXDealDto deal = new FXDealDto(
                "id",
                "EUR",
                "NGN",
                LocalDateTime.now(),
                new BigDecimal("10000.0")
        );

        String testDeal = "{\n" +
                "  \"dealUniqueId\": \"FXD12345\",\n" +
                "  \"fromCurrency\": \"USD\",\n" +
                "  \"toCurrency\": \"EUR\",\n" +
                "  \"dealTimestamp\": \"2023-07-29T15:30:00\",\n" +
                "  \"dealAmount\": 1500.75\n" +
                "}\n";

        CustomResponse<Object> response = new CustomResponse<>(true, false, deal, HttpStatus.CREATED.value());

        when(fxDealService.importFXDeals(deal)).thenReturn(response);

        mockMvc.perform(post("/import")
                        .content(testDeal)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.errorList").doesNotExist())
//                .andExpect(jsonPath("$.data.dealUniqueId").exists());
    }
}