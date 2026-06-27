package com.corporate.food.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WorkingWeekRequest {

    @NotNull
    private Integer weekNumber;

    @NotNull
    private Integer persianYear;

    @Size(max = 150)
    private String weekName;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private Boolean enabled = true;
}
