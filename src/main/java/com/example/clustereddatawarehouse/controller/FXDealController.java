package com.example.clustereddatawarehouse.controller;

import com.example.clustereddatawarehouse.models.CustomResponse;
import com.example.clustereddatawarehouse.models.FXDealDto;
import com.example.clustereddatawarehouse.service.FXDealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FXDealController {
    @Autowired
    private final FXDealService fxDealService;

    public FXDealController(FXDealService fxDealService) {
        this.fxDealService = fxDealService;
    }

    @PostMapping("/import")
    public ResponseEntity<CustomResponse<Object>> importFXDeals(@RequestBody FXDealDto fxDealDto){
        return ResponseEntity.ok(fxDealService.importFXDeals(fxDealDto));
    }
}
