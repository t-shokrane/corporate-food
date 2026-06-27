package com.corporate.food.repository;

import com.corporate.food.domain.entity.Food;
import java.util.List;
import java.util.Optional;

public interface FoodRepository extends BaseRepository<Food, Long> {

    List<Food> findByEnabledTrue();

    Optional<Food> findByNameIgnoreCase(String name);
}
