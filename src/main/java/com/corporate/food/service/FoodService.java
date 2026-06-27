package com.corporate.food.service;

import com.corporate.food.domain.entity.Food;
import com.corporate.food.dto.FoodRequest;
import com.corporate.food.dto.FoodResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.FoodFilterDTO;
import com.corporate.food.mapper.EntityMapper;
import com.corporate.food.repository.BaseRepository;
import com.corporate.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService extends BaseService<Food, Long> {

    private final FoodRepository foodRepository;
    private final EntityMapper entityMapper;

    @Override
    protected BaseRepository<Food, Long> getRepository() {
        return foodRepository;
    }

    @Override
    protected String getResourceName() {
        return "Food";
    }

    public PagedResponse<FoodResponse> findAll(FoodFilterDTO filter) {
        // TODO: Implement business logic using toPageable(filter)
        return PagedResponse.empty(filter);
    }

    public FoodResponse findById(Long id) {
        // TODO: Implement business logic
        return null;
    }

    @Transactional
    public FoodResponse create(FoodRequest request) {
        // TODO: Implement business logic
        return null;
    }

    @Transactional
    public FoodResponse update(Long id, FoodRequest request) {
        // TODO: Implement business logic
        return null;
    }

    @Transactional
    public void delete(Long id) {
        // TODO: Implement business logic
    }
}
