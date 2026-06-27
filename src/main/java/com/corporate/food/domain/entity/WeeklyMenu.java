package com.corporate.food.domain.entity;

import com.corporate.food.domain.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "weekly_menu",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_menu_week_day_food",
                columnNames = {"week_id", "weekday_id", "food_id"}
        )
)
public class WeeklyMenu extends BaseDomain {

    @ManyToOne(optional = false)
    @JoinColumn(name = "week_id", nullable = false)
    private WorkingWeek workingWeek;

    @ManyToOne(optional = false)
    @JoinColumn(name = "weekday_id", nullable = false)
    private WeekDay weekDay;

    @ManyToOne(optional = false)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Boolean enabled = true;
}
