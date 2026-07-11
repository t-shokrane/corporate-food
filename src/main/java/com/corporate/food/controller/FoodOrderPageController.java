package com.corporate.food.controller;

import com.corporate.food.dto.FoodOrderResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.FoodOrderFilterDTO;
import com.corporate.food.service.FoodOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class FoodOrderPageController {


    private final FoodOrderService foodOrderService;


    @GetMapping("/admin/orders")
    public String orders(Model model) {


        FoodOrderFilterDTO filter = new FoodOrderFilterDTO();

        // فعلاً دمو: ادمین همه سفارش‌ها را می‌بیند
        filter.setRole("ADMIN");


        PagedResponse<FoodOrderResponse> orders =
                foodOrderService.findAll(filter);


        model.addAttribute(
                "orders",
                orders.getItems()
        );


        return "admin/orders";

    }

}