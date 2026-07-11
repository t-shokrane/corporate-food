package com.corporate.food.repository;

import com.corporate.food.domain.entity.FoodOrder;
import com.corporate.food.domain.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface FoodOrderRepository extends BaseRepository<FoodOrder, Long> {


    // تعداد سفارش های ثبت شده برای یک غذا در یک روز و هفته
    long countByWorkingWeekIdAndWeekDayIdAndFoodIdAndOrderStatus(
            Long weekId,
            Long weekdayId,
            Long foodId,
            OrderStatus orderStatus
    );



    // سفارش قبلی یک کارمند در یک روز
    Optional<FoodOrder> findByEmployeeIdAndWorkingWeekIdAndWeekDayId(
            Long employeeId,
            Long weekId,
            Long weekdayId
    );



    List<FoodOrder> findByEmployeeIdAndWorkingWeekId(
            Long employeeId,
            Long weekId
    );



    List<FoodOrder> findByWorkingWeekIdAndWeekDayId(
            Long weekId,
            Long weekdayId
    );



    Page<FoodOrder> findByWorkingWeekId(
            Long weekId,
            Pageable pageable
    );



    Page<FoodOrder> findByEmployeeId(
            Long employeeId,
            Pageable pageable
    );



    Page<FoodOrder> findByEmployeeIdAndWorkingWeekId(
            Long employeeId,
            Long weekId,
            Pageable pageable
    );



    Page<FoodOrder> findByEmployeeIdAndWorkingWeekIdAndOrderStatus(
            Long employeeId,
            Long weekId,
            OrderStatus orderStatus,
            Pageable pageable
    );



    Page<FoodOrder> findByEmployeeIdAndOrderStatus(
            Long employeeId,
            OrderStatus orderStatus,
            Pageable pageable
    );



    Page<FoodOrder> findByWorkingWeekIdAndOrderStatus(
            Long weekId,
            OrderStatus orderStatus,
            Pageable pageable
    );



    Page<FoodOrder> findByOrderStatus(
            OrderStatus orderStatus,
            Pageable pageable
    );



    Optional<FoodOrder> findByEmployeeIdAndWorkingWeekIdAndWeekDayIdAndOrderStatus(
            Long employeeId,
            Long weekId,
            Long weekdayId,
            OrderStatus orderStatus
    );



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
            @Param("status") OrderStatus status
    );



    boolean existsByFoodId(Long foodId);

}