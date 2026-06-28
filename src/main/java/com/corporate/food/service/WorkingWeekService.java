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
import com.corporate.food.exception.ResourceNotFoundException;


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
        var pageable = toPageable(filter); // تبدیل فیلتر به Pageable
        var page = workingWeekRepository.findAll(pageable); // گرفتن صفحه هفته‌های کاری
        var responsePage = page.map(entityMapper::toWorkingWeekResponse); // تبدیل هر Week به WorkingWeekResponse
        return PagedResponse.of(responsePage, responsePage.getContent()); // برگرداندن پاسخ صفحه‌بندی شده
    }



    public WorkingWeekResponse findById(Long id) {
        var workingWeek = workingWeekRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Working week", "id", id));

        return entityMapper.toWorkingWeekResponse(workingWeek);
    }


    public PagedResponse<WeekDayResponse> findAllWeekDays(WeekDayFilterDTO filter) {
        var pageable = toPageable(filter);
        var page = weekDayRepository.findAll(pageable);
        var responsePage = page.map(entityMapper::toWeekDayResponse);
        return PagedResponse.of(responsePage, responsePage.getContent());
    }

    @Transactional
    public WorkingWeekResponse create(WorkingWeekRequest request) {
        WorkingWeek workingWeek = (WorkingWeek) entityMapper.toWorkingWeek(request);
        return entityMapper.toWorkingWeekResponse(workingWeekRepository.save(workingWeek));
    }

    @Transactional
    public WorkingWeekResponse update(Long id, WorkingWeekRequest request) {
        var workingWeek = workingWeekRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Working week", "id", id));

        entityMapper.updateWorkingWeekFromRequest(request, workingWeek);

        var savedWorkingWeek = workingWeekRepository.save(workingWeek);

        return entityMapper.toWorkingWeekResponse(savedWorkingWeek);
    }


    @Transactional
public void delete(Long id) {
    // ۱. پیدا کردن هفته کاری، اگر نبود خطا بده
    var workingWeek = workingWeekRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Working week", "id", id));

    // ۲. حذف هفته کاری
    workingWeekRepository.delete(workingWeek);
}


    }


