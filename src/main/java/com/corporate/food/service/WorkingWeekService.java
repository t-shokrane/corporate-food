package com.corporate.food.service;

import com.corporate.food.domain.entity.WorkingWeek;
import com.corporate.food.dto.WeekDayResponse;
import com.corporate.food.dto.WorkingWeekRequest;
import com.corporate.food.dto.WorkingWeekResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.WeekDayFilterDTO;
import com.corporate.food.dto.filter.WorkingWeekFilterDTO;
import com.corporate.food.mapper.EntityMapper;
import com.corporate.food.repository.BaseRepository;
import com.corporate.food.repository.WeekDayRepository;
import com.corporate.food.repository.WorkingWeekRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkingWeekService extends BaseService<WorkingWeek, Long> {

    private final WorkingWeekRepository workingWeekRepository;
    private final WeekDayRepository weekDayRepository;
    private final EntityMapper entityMapper;

    @Override
    protected BaseRepository<WorkingWeek, Long> getRepository() {
        return workingWeekRepository;
    }

    @Override
    protected String getResourceName() {
        return "Working week";
    }

    public PagedResponse<WorkingWeekResponse> findAll(WorkingWeekFilterDTO filter) {
        // TODO: Implement business logic using toPageable(filter)
        return PagedResponse.empty(filter);
    }

    public WorkingWeekResponse findById(Long id) {
        // TODO: Implement business logic
        return null;
    }

    public PagedResponse<WeekDayResponse> findAllWeekDays(WeekDayFilterDTO filter) {
        // TODO: Implement business logic using toPageable(filter)
        return PagedResponse.empty(filter);
    }

    @Transactional
    public WorkingWeekResponse create(WorkingWeekRequest request) {
        // TODO: Implement business logic
        return null;
    }

    @Transactional
    public WorkingWeekResponse update(Long id, WorkingWeekRequest request) {
        // TODO: Implement business logic
        return null;
    }

    @Transactional
    public void delete(Long id) {
        // TODO: Implement business logic
    }
}
