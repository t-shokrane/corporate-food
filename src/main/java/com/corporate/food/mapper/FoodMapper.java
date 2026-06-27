package com.corporate.food.mapper;

import com.corporate.food.domain.entity.Food;
import com.corporate.food.dto.FoodRequest;
import com.corporate.food.dto.FoodResponse;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

    public FoodResponse toResponse(Food food) {
        return FoodResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .description(food.getDescription())
                .price(food.getPrice())
                .enabled(food.getEnabled())
                .createdOn(food.getCreatedOn())
                .updatedOn(food.getUpdatedOn())
                .build();
    }

    public Food toEntity(FoodRequest request) {
        Food food = new Food();
        applyRequest(food, request);
        return food;
    }

    public void applyRequest(Food food, FoodRequest request) {
        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setPrice(request.getPrice());
        food.setEnabled(request.getEnabled() != null ? request.getEnabled() : true);
    }
}
