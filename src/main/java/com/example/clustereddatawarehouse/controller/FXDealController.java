package com.example.clustereddatawarehouse.controller;

import com.example.clustereddatawarehouse.models.CustomResponse;
import com.example.clustereddatawarehouse.models.FXDealDto;
import com.example.clustereddatawarehouse.service.FXDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FXDealController {
    @Autowired
    FXDealService fxDealService;

    @PostMapping("/create")
    public ResponseEntity<CustomResponse<Object>> importFXDeals(@RequestBody FXDealDto fxDealDto){
        return ResponseEntity.ok(fxDealService.importFXDeals(fxDealDto));
    }
}
//junit mockito