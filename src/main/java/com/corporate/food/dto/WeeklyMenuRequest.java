package com.corporate.food.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class WeeklyMenuRequest {

    @NotNull
    private Long weekId;

    @NotNull
    private Long weekdayId;

    @NotNull
    private Long foodId;

    @NotNull
    @Positive
    private Integer capacity;

    private Boolean enabled = true;
}
