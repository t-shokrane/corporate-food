package com.corporate.food.domain.entity;

import com.corporate.food.domain.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "food")
public class Food extends BaseDomain {

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 300)
    private String description;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Boolean enabled = true;

    @OneToMany(mappedBy = "food")
    private List<WeeklyMenu> menuItems = new ArrayList<>();

    @OneToMany(mappedBy = "food")
    private List<FoodOrder> orders = new ArrayList<>();
}
