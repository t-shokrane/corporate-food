package com.corporate.food.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WeeklyMenuResponse {

    Long id;
    Long weekId;
    Long weekdayId;
    String weekdayPersianName;
    Long foodId;
    String foodName;
    Integer capacity;
    Boolean enabled;
    Long registeredOrderCount;
    LocalDateTime createdOn;
}
