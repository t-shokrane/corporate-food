package com.corporate.food.controller;

import com.corporate.food.dto.EmployeeResponse;
import com.corporate.food.dto.ApiResponse;
import com.corporate.food.repository.EmployeeRepository;
import com.corporate.food.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee-panel")
@RequiredArgsConstructor
public class EmployeePanelController extends BaseController {


    private final EmployeeRepository employeeRepository;
    private final EntityMapper entityMapper;


    @GetMapping("/me")
    public ApiResponse<EmployeeResponse> currentUser(
            Authentication authentication) {


        String username = authentication.getName();


        var employee =
                employeeRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException("Employee not found"));


        return ok(entityMapper.toEmployeeResponse(employee));
    }
}