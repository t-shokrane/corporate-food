package com.corporate.food.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String index(Model model) {

        model.addAttribute("title", "Corporate Food Admin");
        model.addAttribute("apiBase", "/api/v1");

        return "admin/admin-panel";
    }


    @GetMapping("/employees-page")
    public String employeesPage() {

        return "admin/employee";

    }

}