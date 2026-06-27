package com.corporate.food.domain.entity;

import com.corporate.food.domain.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "week_day")
public class WeekDay extends BaseDomain {

    @Column(name = "week_day_number", nullable = false, unique = true)
    private Integer weekDayNumber;

    @Column(name = "persian_name", length = 50)
    private String persianName;

    @Column(name = "english_name", length = 50)
    private String englishName;
}
