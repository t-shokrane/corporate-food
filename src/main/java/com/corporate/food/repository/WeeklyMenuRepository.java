package com.corporate.food.repository;

import com.corporate.food.domain.entity.WeeklyMenu;
import java.util.List;
import java.util.Optional;
public interface WeeklyMenuRepository extends BaseRepository<WeeklyMenu, Long> {

    Optional<WeeklyMenu> findByWorkingWeekIdAndWeekDayIdAndFoodIdAndEnabledTrue(
            Long weekId, Long weekdayId, Long foodId);

    List<WeeklyMenu> findByWorkingWeekIdAndWeekDayIdAndEnabledTrue(Long weekId, Long weekdayId);

    List<WeeklyMenu> findByWorkingWeekIdAndEnabledTrue(Long weekId);
}
