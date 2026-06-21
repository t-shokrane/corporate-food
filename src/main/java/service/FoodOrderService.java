package com.foodsystem.food_order_app.service;

import com.foodsystem.food_order_app.model.Food;
import com.foodsystem.food_order_app.model.FoodOrder;
import com.foodsystem.food_order_app.model.User;
import com.foodsystem.food_order_app.repository.FoodOrderRepository;
import com.foodsystem.food_order_app.repository.FoodRepository;
import com.foodsystem.food_order_app.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class FoodOrderService {

    private final FoodOrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    public FoodOrderService(FoodOrderRepository orderRepository,
                            FoodRepository foodRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
    }

    public void placeOrder(Long foodId, Long userId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FoodOrder order = new FoodOrder();
        order.setFood(food);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        orderRepository.save(order);
    }
}