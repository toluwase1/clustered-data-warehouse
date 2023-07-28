package com.example.clustereddatawarehouse.response;

import lombok.Data;

@Data
public class CustomResponse<T> {
    private boolean success;
    private boolean error;
    private T responseData;
    private int statusCode;

    public CustomResponse(boolean success, boolean error, T responseData, int statusCode) {
        this.success = success;
        this.error = error;
        this.responseData = responseData;
        this.statusCode = statusCode;
    }
    public boolean isError() {
        return error;
    }
}