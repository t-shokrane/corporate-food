package com.foodsystem.food_order_app.service;

import com.foodsystem.food_order_app.model.Food;
import com.foodsystem.food_order_app.repository.FoodRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }
}