package com.corporate.food.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class EmployeePageController {

    @GetMapping
    public String index() {
        return "admin/employee-panel";
    }
    }