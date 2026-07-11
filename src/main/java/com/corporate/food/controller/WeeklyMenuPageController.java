package com.corporate.food.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeeklyMenuPageController {

    @GetMapping("/admin/weekly-menus")
    public String weeklyMenus() {
        return "admin/weekly-menu";
    }

}