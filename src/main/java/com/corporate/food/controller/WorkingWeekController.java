package com.corporate.food.controller;

import com.corporate.food.dto.ApiResponse;
import com.corporate.food.dto.WeekDayResponse;
import com.corporate.food.dto.WorkingWeekRequest;
import com.corporate.food.dto.WorkingWeekResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.WeekDayFilterDTO;
import com.corporate.food.dto.filter.WorkingWeekFilterDTO;
import com.corporate.food.service.WorkingWeekService;
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
@RequestMapping("/api/v1/working-weeks")
@RequiredArgsConstructor
public class WorkingWeekController extends BaseController {

    private final WorkingWeekService workingWeekService;

    @GetMapping
    public ApiResponse<PagedResponse<WorkingWeekResponse>> findAll(@Valid @ModelAttribute WorkingWeekFilterDTO filter) {
        return ok(workingWeekService.findAll(filter));
    }

    @GetMapping("/enabled")
    public ApiResponse<PagedResponse<WorkingWeekResponse>> findEnabled(
            @Valid @ModelAttribute WorkingWeekFilterDTO filter) {
        filter.setEnabled(true);
        return ok(workingWeekService.findAll(filter));
    }

    @GetMapping("/{id}")
    public ApiResponse<WorkingWeekResponse> findById(@PathVariable Long id) {
        return ok(workingWeekService.findById(id));
    }

    @GetMapping("/week-days")
    public ApiResponse<PagedResponse<WeekDayResponse>> findWeekDays(@Valid @ModelAttribute WeekDayFilterDTO filter) {
        return ok(workingWeekService.findAllWeekDays(filter));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<WorkingWeekResponse> create(@Valid @RequestBody WorkingWeekRequest request) {
        return created(workingWeekService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<WorkingWeekResponse> update(@PathVariable Long id, @Valid @RequestBody WorkingWeekRequest request) {
        return ok("Working week updated successfully", workingWeekService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        workingWeekService.delete(id);
        return deleted();
    }
}
