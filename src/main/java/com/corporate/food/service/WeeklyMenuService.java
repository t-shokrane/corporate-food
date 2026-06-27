package com.corporate.food.service;

import com.corporate.food.domain.entity.WeeklyMenu;
import com.corporate.food.dto.WeeklyMenuRequest;
import com.corporate.food.dto.WeeklyMenuResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.WeeklyMenuFilterDTO;
import com.corporate.food.mapper.EntityMapper;
import com.corporate.food.repository.BaseRepository;
import com.corporate.food.repository.FoodOrderRepository;
import com.corporate.food.repository.WeeklyMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeeklyMenuService extends BaseService<WeeklyMenu, Long> {

    private final WeeklyMenuRepository weeklyMenuRepository;
    private final WorkingWeekService workingWeekService;
    private final FoodService foodService;
    private final FoodOrderRepository foodOrderRepository;
    private final EntityMapper entityMapper;

    @Override
    protected BaseRepository<WeeklyMenu, Long> getRepository() {
        return weeklyMenuRepository;
    }

    @Override
    protected String getResourceName() {
        return "Weekly menu";
    }

    public PagedResponse<WeeklyMenuResponse> findAll(WeeklyMenuFilterDTO filter) {
        // TODO: Implement business logic using toPageable(filter)
        return PagedResponse.empty(filter);
    }

    public WeeklyMenuResponse findById(Long id) {
        // TODO: Implement business logic
        return null;
    }

    @Transactional
    public WeeklyMenuResponse create(WeeklyMenuRequest request) {
        // TODO: Implement business logic
        return null;
    }

    @Transactional
    public WeeklyMenuResponse update(Long id, WeeklyMenuRequest request) {
        // TODO: Implement business logic
        return null;
    }

    @Transactional
    public void delete(Long id) {
        // TODO: Implement business logic
    }
}
