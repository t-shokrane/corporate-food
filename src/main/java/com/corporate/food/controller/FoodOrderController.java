package com.corporate.food.controller;

import com.corporate.food.dto.ApiResponse;
import com.corporate.food.dto.FoodOrderRequest;
import com.corporate.food.dto.FoodOrderResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.FoodOrderFilterDTO;
import com.corporate.food.service.FoodOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/food-orders")
@RequiredArgsConstructor
public class FoodOrderController extends BaseController {

    private final FoodOrderService foodOrderService;

    @GetMapping
    public ApiResponse<PagedResponse<FoodOrderResponse>> findAll(@Valid @ModelAttribute FoodOrderFilterDTO filter) {
        return ok(foodOrderService.findAll(filter));
    }

    @GetMapping("/{id}")
    public ApiResponse<FoodOrderResponse> findById(@PathVariable Long id) {
        return ok(foodOrderService.findById(id));
    }

    @GetMapping("/employee/{employeeId}/week/{weekId}")
    public ApiResponse<PagedResponse<FoodOrderResponse>> findByEmployeeAndWeek(
            @PathVariable Long employeeId,
            @PathVariable Long weekId,
            @Valid @ModelAttribute FoodOrderFilterDTO filter) {
        filter.setEmployeeId(employeeId);
        filter.setWeekId(weekId);
        return ok(foodOrderService.findAll(filter));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<FoodOrderResponse> placeOrder(@Valid @RequestBody FoodOrderRequest request) {
        return created(foodOrderService.placeOrder(request));

    }

    @PostMapping("/{id}/cancel")
    public ApiResponse<FoodOrderResponse> cancelOrder(@PathVariable Long id) {
        return ok(foodOrderService.cancelOrder(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        foodOrderService.delete(id);
        return deleted();
    }
}
