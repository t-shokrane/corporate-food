package com.corporate.food.repository;

import com.corporate.food.domain.entity.FoodOrder;
import com.corporate.food.domain.enums.OrderStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodOrderRepository extends BaseRepository<FoodOrder, Long> {

    long countByWorkingWeekIdAndWeekDayIdAndFoodIdAndOrderStatus(
            Long weekId, Long weekdayId, Long foodId, OrderStatus orderStatus);

    Optional<FoodOrder> findByEmployeeIdAndWorkingWeekIdAndWeekDayId(
            Long employeeId, Long weekId, Long weekdayId);

    List<FoodOrder> findByEmployeeIdAndWorkingWeekId(Long employeeId, Long weekId);

    List<FoodOrder> findByWorkingWeekIdAndWeekDayId(Long weekId, Long weekdayId);

    @Query("""
            SELECT fo FROM FoodOrder fo
            WHERE fo.workingWeek.id = :weekId
              AND fo.weekDay.id = :weekdayId
              AND fo.food.id = :foodId
              AND fo.orderStatus = :status
            """)
    List<FoodOrder> findRegisteredOrders(
            @Param("weekId") Long weekId,
            @Param("weekdayId") Long weekdayId,
            @Param("foodId") Long foodId,
            @Param("status") OrderStatus status);
}
