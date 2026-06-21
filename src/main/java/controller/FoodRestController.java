package com.foodsystem.food_order_app.controller;

import com.foodsystem.food_order_app.model.Food;
import com.foodsystem.food_order_app.service.FoodOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FoodRestController {

    @Autowired
    private FoodOrderService foodOrderService;

    @GetMapping("/foods")
    public List<Food> getAllFoods() {
        return foodOrderService.getAllFoods(); // حالا این متد وجود دارد و خطا نمی‌دهد
    }
}