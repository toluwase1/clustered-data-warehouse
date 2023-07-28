package com.example.clustereddatawarehouse.controller;

import com.example.clustereddatawarehouse.models.FXDeal;
import com.example.clustereddatawarehouse.response.CustomResponse;
import com.example.clustereddatawarehouse.service.FXDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FXDealController {
    @Autowired
    FXDealService fxDealService;

    @PostMapping("/create")
    public CustomResponse<String> importFXDeals(@RequestBody FXDeal fxDeal){
        return fxDealService.importFXDeals(fxDeal);

    }
}
