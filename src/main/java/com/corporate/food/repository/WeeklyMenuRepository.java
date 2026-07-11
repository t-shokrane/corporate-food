package com.corporate.food.repository;

import com.corporate.food.domain.entity.WeeklyMenu;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WeeklyMenuRepository extends BaseRepository<WeeklyMenu, Long> {


    Optional<WeeklyMenu> findByWorkingWeekIdAndWeekDayIdAndFoodIdAndEnabledTrue(
            Long weekId,
            Long weekdayId,
            Long foodId
    );


    // برای بررسی ظرفیت غذا هنگام ثبت سفارش
    Optional<WeeklyMenu> findByWorkingWeekIdAndWeekDayIdAndFoodId(
            Long weekId,
            Long weekdayId,
            Long foodId
    );


    Page<WeeklyMenu> findByWorkingWeekId(
            Long weekId,
            Pageable pageable
    );


    Page<WeeklyMenu> findByWorkingWeekIdAndWeekDayId(
            Long weekId,
            Long weekdayId,
            Pageable pageable
    );


    List<WeeklyMenu> findByWorkingWeekIdAndWeekDayIdAndEnabledTrue(
            Long weekId,
            Long weekdayId
    );


    List<WeeklyMenu> findByWorkingWeekIdAndEnabledTrue(
            Long weekId
    );


    boolean existsByWorkingWeekId(
            Long workingWeekId
    );

}