package com.corporate.food.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompanyPageController {

    @GetMapping("/companies")
    public String companies() {
        return "admin/company";
    }
}