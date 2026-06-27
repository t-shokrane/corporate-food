package com.corporate.food.controller;

import com.corporate.food.dto.ApiResponse;
import com.corporate.food.dto.WeeklyMenuRequest;
import com.corporate.food.dto.WeeklyMenuResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.WeeklyMenuFilterDTO;
import com.corporate.food.service.WeeklyMenuService;
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
@RequestMapping("/api/v1/weekly-menus")
@RequiredArgsConstructor
public class WeeklyMenuController extends BaseController {

    private final WeeklyMenuService weeklyMenuService;

    @GetMapping("/week/{weekId}")
    public ApiResponse<PagedResponse<WeeklyMenuResponse>> findByWeek(
            @PathVariable Long weekId, @Valid @ModelAttribute WeeklyMenuFilterDTO filter) {
        filter.setWeekId(weekId);
        return ok(weeklyMenuService.findAll(filter));
    }

    @GetMapping("/week/{weekId}/day/{weekdayId}")
    public ApiResponse<PagedResponse<WeeklyMenuResponse>> findByWeekAndDay(
            @PathVariable Long weekId,
            @PathVariable Long weekdayId,
            @Valid @ModelAttribute WeeklyMenuFilterDTO filter) {
        filter.setWeekId(weekId);
        filter.setWeekdayId(weekdayId);
        return ok(weeklyMenuService.findAll(filter));
    }

    @GetMapping("/{id}")
    public ApiResponse<WeeklyMenuResponse> findById(@PathVariable Long id) {
        return ok(weeklyMenuService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<WeeklyMenuResponse> create(@Valid @RequestBody WeeklyMenuRequest request) {
        return created(weeklyMenuService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<WeeklyMenuResponse> update(@PathVariable Long id, @Valid @RequestBody WeeklyMenuRequest request) {
        return ok("Weekly menu updated successfully", weeklyMenuService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        weeklyMenuService.delete(id);
        return deleted();
    }
}
