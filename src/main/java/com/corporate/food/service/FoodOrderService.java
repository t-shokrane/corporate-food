package com.corporate.food.service;

import com.corporate.food.domain.entity.FoodOrder;
import com.corporate.food.dto.FoodOrderRequest;
import com.corporate.food.dto.FoodOrderResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.FoodOrderFilterDTO;
import com.corporate.food.mapper.EntityMapper;
import com.corporate.food.repository.BaseRepository;
import com.corporate.food.repository.FoodOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodOrderService extends BaseService<FoodOrder, Long> {

    private final FoodOrderRepository foodOrderRepository;
    private final EmployeeService employeeService;
    private final WorkingWeekService workingWeekService;
    private final FoodService foodService;
    private final WeeklyMenuService weeklyMenuService;
    private final EntityMapper entityMapper;

    @Override
    protected BaseRepository<FoodOrder, Long> getRepository() {
        return foodOrderRepository;
    }

    @Override
    protected String getResourceName() {
        return "Food order";
    }

    public PagedResponse<FoodOrderResponse> findAll(FoodOrderFilterDTO filter) {
        // TODO: Implement business logic using toPageable(filter)
        return PagedResponse.empty(filter);
    }

    public FoodOrderResponse findById(Long id) {
        // TODO: Implement business logic
        return null;
    }

    @Transactional
    public FoodOrderResponse placeOrder(FoodOrderRequest request) {
        // TODO: Implement business logic
        return null;
    }

    @Transactional
    public FoodOrderResponse cancelOrder(Long id) {
        // TODO: Implement business logic
        return null;
    }

    @Transactional
    public void delete(Long id) {
        // TODO: Implement business logic
    }
}
