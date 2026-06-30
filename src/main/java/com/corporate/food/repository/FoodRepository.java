package com.corporate.food.repository;

import com.corporate.food.domain.entity.Food;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodRepository extends BaseRepository<Food, Long> {

    List<Food> findByEnabledTrue();

    Optional<Food> findByNameIgnoreCase(String name);
    Page<Food> findByEnabled(Boolean enabled, Pageable pageable);
}
