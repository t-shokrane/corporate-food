package com.corporate.food.service;

import com.corporate.food.domain.entity.WeeklyMenu;
import com.corporate.food.dto.WeeklyMenuRequest;
import com.corporate.food.dto.WeeklyMenuResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.WeeklyMenuFilterDTO;
import com.corporate.food.exception.ResourceNotFoundException;
import com.corporate.food.mapper.EntityMapper;
import com.corporate.food.repository.BaseRepository;
import com.corporate.food.repository.FoodOrderRepository;
import com.corporate.food.repository.WeeklyMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeeklyMenuService extends BaseService<WeeklyMenu, Long> {

    private final WeeklyMenuRepository weeklyMenuRepository;
    private final WorkingWeekService workingWeekService;
    private final FoodService foodService;
    private final FoodOrderRepository foodOrderRepository;
    private final EntityMapper entityMapper;

    @Override
    protected BaseRepository<WeeklyMenu, Long> getRepository() {
        return weeklyMenuRepository;
    }

    @Override
    protected String getResourceName() {
        return "Weekly menu";
    }

    public PagedResponse<WeeklyMenuResponse> findAll(WeeklyMenuFilterDTO filter) {
        // TODO: Filter not applied — filter.weekId (and weekdayId) ignored; repository uses findAll without predicates
        var pageable = toPageable(filter);
        var page = weeklyMenuRepository.findAll(pageable);
        // TODO: GET list returns 500 — lazy-loaded workingWeek/weekDay/food accessed outside session during toWeeklyMenuResponse mapping
        var responsePage = page.map(entityMapper::toWeeklyMenuResponse);
        return PagedResponse.of(responsePage, responsePage.getContent());
    }

    public WeeklyMenuResponse findById(Long id) {
        var weeklyMenu = weeklyMenuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weekly menu not found with id: " + id));
        return entityMapper.toWeeklyMenuResponse(weeklyMenu);
    }

    @Transactional
    public WeeklyMenuResponse create(WeeklyMenuRequest request) {
        // TODO: POST broken — entityMapper.toWeeklyMenu returns Object; unsafe cast may fail or produce invalid entity for save
        // TODO: WeeklyMenu create incomplete — workingWeek, weekDay, and food relations from request IDs are not resolved and set before save
        WeeklyMenu weeklyMenu = (WeeklyMenu) entityMapper.toWeeklyMenu(request);
        return entityMapper.toWeeklyMenuResponse(weeklyMenuRepository.save(weeklyMenu));
    }

    @Transactional
    public WeeklyMenuResponse update(Long id, WeeklyMenuRequest request) {
        var weeklyMenu = weeklyMenuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weekly menu not found with id: " + id));

        // TODO: WeeklyMenu update incomplete — updateWeeklyMenuFromRequest does not resolve/set workingWeek, weekDay, and food from request IDs
        entityMapper.updateWeeklyMenuFromRequest(request, weeklyMenu);

        var savedWeeklyMenu = weeklyMenuRepository.save(weeklyMenu);
        return entityMapper.toWeeklyMenuResponse(savedWeeklyMenu);
    }


    @Transactional
    public void delete(Long id) {
        var weeklyMenu = weeklyMenuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weekly menu not found with id: " + id));
        weeklyMenuRepository.delete(weeklyMenu);

        }
    }