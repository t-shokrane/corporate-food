package com.corporate.food.controller;

import com.corporate.food.dto.ApiResponse;
import com.corporate.food.dto.FoodRequest;
import com.corporate.food.dto.FoodResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.FoodFilterDTO;
import com.corporate.food.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
public class FoodController extends BaseController {

    private final FoodService foodService;

    @GetMapping
    public ApiResponse<PagedResponse<FoodResponse>> findAll(@Valid @ModelAttribute FoodFilterDTO filter) {
        return ok(foodService.findAll(filter));
    }

    @GetMapping("/enabled")
    public ApiResponse<PagedResponse<FoodResponse>> findEnabled(@Valid @ModelAttribute FoodFilterDTO filter) {
        filter.setEnabled(true);
        return ok(foodService.findAll(filter));
    }

    @GetMapping("/{id}")
    public ApiResponse<FoodResponse> findById(@PathVariable Long id) {
        return ok(foodService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<FoodResponse> create(@Valid @RequestBody FoodRequest request) {
        return created(foodService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<FoodResponse> update(@PathVariable Long id, @Valid @RequestBody FoodRequest request) {
        return ok("Food updated successfully", foodService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        foodService.delete(id);
        return deleted();
    }
}
