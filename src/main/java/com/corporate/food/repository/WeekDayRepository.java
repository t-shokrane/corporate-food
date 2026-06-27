package com.corporate.food.repository;

import com.corporate.food.domain.entity.WeekDay;
import java.util.Optional;
public interface WeekDayRepository extends BaseRepository<WeekDay, Long> {

    Optional<WeekDay> findByWeekDayNumber(Integer weekDayNumber);
}
