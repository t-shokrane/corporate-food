package com.corporate.food.service;

import com.corporate.food.domain.entity.Food;
import com.corporate.food.dto.FoodRequest;
import com.corporate.food.dto.FoodResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.FoodFilterDTO;
import com.corporate.food.exception.ResourceNotFoundException;
import com.corporate.food.mapper.EntityMapper;
import com.corporate.food.repository.BaseRepository;
import com.corporate.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.corporate.food.exception.BusinessException;
import com.corporate.food.repository.FoodOrderRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService extends BaseService<Food, Long> {

    private final FoodRepository foodRepository;
    private final EntityMapper entityMapper;
    private final FoodOrderRepository foodOrderRepository;

    @Override
    protected BaseRepository<Food, Long> getRepository() {
        return foodRepository;
    }

    @Override
    protected String getResourceName() {
        return "Food";
    }

    public PagedResponse<FoodResponse> findAll(FoodFilterDTO filter) {

        var pageable = toPageable(filter);

        var page = filter.getEnabled() == null
                ? foodRepository.findAll(pageable)
                : foodRepository.findByEnabled(filter.getEnabled(), pageable);

        var responsePage = page.map(entityMapper::toFoodResponse);

        return PagedResponse.of(responsePage, responsePage.getContent());
    }

    public FoodResponse findById(Long id) {
        return foodRepository.findById(id)
                .map(entityMapper::toFoodResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found with id: " + id));
    }

    @Transactional
    public FoodResponse create(FoodRequest request) {
        // TODO: POST broken — entityMapper.toFood returns Object; unsafe cast may fail or produce invalid entity for save
        Food food = entityMapper.toFood(request);
        return entityMapper.toFoodResponse(foodRepository.save(food));
    }



    @Transactional
    public FoodResponse update(Long id, FoodRequest request) {
        var food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found with id: " + id));
        entityMapper.updateFoodFromRequest(request, food);
        return entityMapper.toFoodResponse(foodRepository.save(food));
    }

    @Transactional
    public void delete(Long id) {

        var food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found with id: " + id));

        if (foodOrderRepository.existsByFoodId(id)) {
            throw new BusinessException("Cannot delete food because it has food orders.");
        }

        foodRepository.delete(food);
    }
    }
