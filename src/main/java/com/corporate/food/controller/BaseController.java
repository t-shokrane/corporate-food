package com.corporate.food.controller;

import com.corporate.food.dto.ApiResponse;

public abstract class BaseController {

    protected <T> ApiResponse<T> ok(T data) {
        return ApiResponse.success(data);
    }

    protected <T> ApiResponse<T> ok(String message, T data) {
        return ApiResponse.success(message, data);
    }

    protected <T> ApiResponse<T> created(T data) {
        return ApiResponse.created(data);
    }

    protected ApiResponse<Void> deleted() {
        return ApiResponse.success("Resource deleted successfully", null);
    }
}
