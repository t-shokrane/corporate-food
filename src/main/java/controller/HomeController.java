package com.foodsystem.food_order_app.controller;

import com.foodsystem.food_order_app.service.FoodService;
import com.foodsystem.food_order_app.service.FoodOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodOrderService foodOrderService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("foods", foodService.getAllFoods());
        return "index";
    }

    @PostMapping("/order/{foodId}")
    public String placeOrder(@PathVariable Long foodId) {

        Long userId = 1L; // فعلاً تستی

        foodOrderService.placeOrder(foodId, userId);

        return "redirect:/?success=true";
    }
}