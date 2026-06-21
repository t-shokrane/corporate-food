package com.foodsystem.food_order_app.repository;

import com.foodsystem.food_order_app.model.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
}