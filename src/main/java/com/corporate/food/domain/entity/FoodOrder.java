package com.corporate.food.domain.entity;

import com.corporate.food.domain.BaseDomain;
import com.corporate.food.domain.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "food_order",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_employee_day",
                columnNames = {"employee_id", "week_id", "weekday_id"}
        )
)
public class FoodOrder extends BaseDomain {

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "week_id", nullable = false)
    private WorkingWeek workingWeek;

    @ManyToOne(optional = false)
    @JoinColumn(name = "weekday_id", nullable = false)
    private WeekDay weekDay;

    @ManyToOne(optional = false)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 20)
    private OrderStatus orderStatus = OrderStatus.REGISTERED;

    @Column(name = "ordered_at", nullable = false)
    private LocalDateTime orderedAt = LocalDateTime.now();

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;
}
