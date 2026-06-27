package com.corporate.food.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FoodRequest {

    @NotBlank
    @Size(max = 150)
    private String name;

    @Size(max = 300)
    private String description;

    @NotNull
    @Positive
    private Long price;

    private Boolean enabled = true;
}
