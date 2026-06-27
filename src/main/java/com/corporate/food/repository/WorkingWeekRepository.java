package com.corporate.food.repository;

import com.corporate.food.domain.entity.WorkingWeek;
import java.util.List;
import java.util.Optional;
public interface WorkingWeekRepository extends BaseRepository<WorkingWeek, Long> {

    Optional<WorkingWeek> findByWeekNumberAndPersianYear(Integer weekNumber, Integer persianYear);

    List<WorkingWeek> findByEnabledTrueOrderByStartDateDesc();
}
