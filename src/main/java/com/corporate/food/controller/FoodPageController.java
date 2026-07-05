package com.corporate.food.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FoodPageController {

    @GetMapping("/foods")
    public String foods() {
        return "admin/food";
    }
}