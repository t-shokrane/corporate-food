package com.corporate.food.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FoodResponse {

    Long id;
    String name;
    String description;
    Long price;
    Boolean enabled;
    LocalDateTime createdOn;
    LocalDateTime updatedOn;
}
