package com.corporate.food.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WorkingWeekResponse {

    Long id;
    Integer weekNumber;
    Integer persianYear;
    String weekName;
    LocalDate startDate;
    LocalDate endDate;
    Boolean enabled;
    LocalDateTime createdOn;
    LocalDateTime updatedOn;
}
