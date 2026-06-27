package com.corporate.food.dto;

import com.corporate.food.domain.enums.OrderStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FoodOrderResponse {

    Long id;
    Long employeeId;
    String employeeName;
    Long weekId;
    Long weekdayId;
    String weekdayPersianName;
    Long foodId;
    String foodName;
    OrderStatus orderStatus;
    LocalDateTime orderedAt;
    LocalDateTime cancelledAt;
    LocalDateTime createdOn;
}
