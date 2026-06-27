package com.corporate.food.domain.entity;

import com.corporate.food.domain.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "working_week",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_week_year_number",
                columnNames = {"week_number", "persian_year"}
        )
)
public class WorkingWeek extends BaseDomain {

    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;

    @Column(name = "persian_year", nullable = false)
    private Integer persianYear;

    @Column(name = "week_name", length = 150)
    private String weekName;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Boolean enabled = true;

    @OneToMany(mappedBy = "workingWeek")
    private List<WeeklyMenu> menuItems = new ArrayList<>();

    @OneToMany(mappedBy = "workingWeek")
    private List<FoodOrder> orders = new ArrayList<>();
}
