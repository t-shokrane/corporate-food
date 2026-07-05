package com.corporate.food.service;

import com.corporate.food.domain.entity.*;
import com.corporate.food.domain.enums.OrderStatus;
import com.corporate.food.dto.FoodOrderRequest;
import com.corporate.food.dto.FoodOrderResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.FoodOrderFilterDTO;
import com.corporate.food.exception.BusinessException;
import com.corporate.food.exception.ResourceNotFoundException;
import com.corporate.food.mapper.EntityMapper;
import com.corporate.food.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodOrderService extends BaseService<FoodOrder, Long> {

    private final FoodOrderRepository foodOrderRepository;
    private final WorkingWeekService workingWeekService;
    private final FoodService foodService;
    private final WeeklyMenuService weeklyMenuService;
    private final EntityMapper entityMapper;
    private final EmployeeRepository employeeRepository;
    private final WorkingWeekRepository workingWeekRepository;
    private final WeekDayRepository weekDayRepository;
    private final FoodRepository foodRepository;

    @Override
    protected BaseRepository<FoodOrder, Long> getRepository() {
        return foodOrderRepository;
    }

    @Override
    protected String getResourceName() {
        return "Food order";
    }

    public PagedResponse<FoodOrderResponse> findAll(FoodOrderFilterDTO filter) {

        var pageable = toPageable(filter);

        Page<FoodOrder> page;

        // 👇 هم کارمند هم هفته
        if (filter.getEmployeeId() != null && filter.getWeekId() != null) {

            page = foodOrderRepository.findByEmployeeIdAndWorkingWeekIdAndOrderStatus(
                    filter.getEmployeeId(),
                    filter.getWeekId(),
                    OrderStatus.REGISTERED,
                    pageable
            );
        }

        // 👇 فقط کارمند
        else if (filter.getEmployeeId() != null) {

            page = foodOrderRepository.findByEmployeeIdAndOrderStatus(
                    filter.getEmployeeId(),
                    OrderStatus.REGISTERED,
                    pageable
            );
        }

        // 👇 فقط هفته
        else if (filter.getWeekId() != null) {

            page = foodOrderRepository.findByWorkingWeekIdAndOrderStatus(
                    filter.getWeekId(),
                    OrderStatus.REGISTERED,
                    pageable
            );
        }

        // 👇 همه سفارش‌های فعال
        else {

            page = foodOrderRepository.findByOrderStatus(
                    OrderStatus.REGISTERED,
                    pageable
            );
        }

        var responsePage = page.map(entityMapper::toFoodOrderResponse);

        return PagedResponse.of(responsePage, responsePage.getContent());
    }

    public FoodOrderResponse findById(Long id) {
        return foodOrderRepository.findById(id)
                .map(entityMapper::toFoodOrderResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }


    @Transactional
    public FoodOrderResponse placeOrder(FoodOrderRequest request) {

        var employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        var workingWeek = workingWeekRepository.findById(request.getWeekId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Working week not found with id: " + request.getWeekId()));

        var weekDay = weekDayRepository.findById(request.getWeekdayId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Week day not found with id: " + request.getWeekdayId()));

        var food = foodRepository.findById(request.getFoodId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food not found with id: " + request.getFoodId()));

        var existingOrder = foodOrderRepository
                .findByEmployeeIdAndWorkingWeekIdAndWeekDayId(
                        request.getEmployeeId(),
                        request.getWeekId(),
                        request.getWeekdayId()
                );

        if (existingOrder.isPresent()) {

            FoodOrder order = existingOrder.get();

            if (order.getOrderStatus() == OrderStatus.CANCELLED) {

                order.setOrderStatus(OrderStatus.REGISTERED);
                order.setCancelledAt(null);
                order.setFood(food);

                var savedOrder = foodOrderRepository.save(order);
                return entityMapper.toFoodOrderResponse(savedOrder);
            }

            throw new BusinessException("Order already exists for this employee in this week day");
        }

        FoodOrder order = new FoodOrder();
        order.setEmployee(employee);
        order.setWorkingWeek(workingWeek);
        order.setWeekDay(weekDay);
        order.setFood(food);
        order.setOrderStatus(OrderStatus.REGISTERED);

        var savedOrder = foodOrderRepository.save(order);

        return entityMapper.toFoodOrderResponse(savedOrder);
    }
        @Transactional
        public FoodOrderResponse cancelOrder (Long id){
            var order = foodOrderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Food order not found with id: " + id));

            order.setOrderStatus(OrderStatus.CANCELLED);
            order.setCancelledAt(LocalDateTime.now());

            return entityMapper.toFoodOrderResponse(foodOrderRepository.save(order));
        }



    @Transactional
    public void delete(Long id) {

        var order = foodOrderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found with id: " + id));

        foodOrderRepository.delete(order);
    }
    }
