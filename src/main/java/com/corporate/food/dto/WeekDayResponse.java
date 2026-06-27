package com.corporate.food.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WeekDayResponse {

    Long id;
    Integer weekDayNumber;
    String persianName;
    String englishName;
}
