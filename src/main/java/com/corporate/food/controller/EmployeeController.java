package com.corporate.food.controller;

import com.corporate.food.dto.ApiResponse;
import com.corporate.food.dto.EmployeeRequest;
import com.corporate.food.dto.EmployeeResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.EmployeeFilterDTO;
import com.corporate.food.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController extends BaseController {

    private final EmployeeService employeeService;

    @GetMapping
    public ApiResponse<PagedResponse<EmployeeResponse>> findAll(@Valid @ModelAttribute EmployeeFilterDTO filter) {
        return ok(employeeService.findAll(filter));
    }

    @GetMapping("/{id}")
    public ApiResponse<EmployeeResponse> findById(@PathVariable Long id) {
        return ok(employeeService.findById(id));
    }

    @GetMapping("/company/{companyId}")
    public ApiResponse<PagedResponse<EmployeeResponse>> findByCompany(
            @PathVariable Long companyId, @Valid @ModelAttribute EmployeeFilterDTO filter) {
        filter.setCompanyId(companyId);
        return ok(employeeService.findAll(filter));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<EmployeeResponse> create(@Valid @RequestBody EmployeeRequest request) {
        return created(employeeService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<EmployeeResponse> update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest request) {
        return ok("Employee updated successfully", employeeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return deleted();
    }
}
