package com.corporate.food.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorkingWeekPageController {


    @GetMapping("/admin/working-weeks")
    public String workingWeeks(){

        return "admin/working-week";

    }

}