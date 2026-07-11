package com.corporate.food.controller;

import com.corporate.food.domain.entity.Employee;
import com.corporate.food.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class EmployeeProfileController {


    private final EmployeeRepository employeeRepository;



    @GetMapping
    public Employee getProfile(Authentication authentication){


        String username =
                authentication.getName();


        return employeeRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

    }

}