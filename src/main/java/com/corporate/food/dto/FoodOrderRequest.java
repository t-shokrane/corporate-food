package com.corporate.food.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FoodOrderRequest {

    @NotNull
    private Long employeeId;

    @NotNull
    private Long weekId;

    @NotNull
    private Long weekdayId;

    @NotNull
    private Long foodId;
}
