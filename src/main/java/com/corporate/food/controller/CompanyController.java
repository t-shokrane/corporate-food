package com.corporate.food.controller;

import com.corporate.food.dto.ApiResponse;
import com.corporate.food.dto.CompanyRequest;
import com.corporate.food.dto.CompanyResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.CompanyFilterDTO;
import com.corporate.food.service.CompanyService;
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
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController extends BaseController {

    private final CompanyService companyService;

    @GetMapping
    public ApiResponse<PagedResponse<CompanyResponse>> findAll(@Valid @ModelAttribute CompanyFilterDTO filter) {
        return ok(companyService.findAll(filter));
    }

    @GetMapping("/{id}")
    public ApiResponse<CompanyResponse> findById(@PathVariable Long id) {
        return ok(companyService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CompanyResponse> create(@Valid @RequestBody CompanyRequest request) {
        return created(companyService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<CompanyResponse> update(@PathVariable Long id, @Valid @RequestBody CompanyRequest request) {
        return ok("Company updated successfully", companyService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        companyService.delete(id);
        return deleted();
    }
}
