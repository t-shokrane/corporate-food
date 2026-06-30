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
import com.corporate.food.repository.WeekDayRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeeklyMenuService extends BaseService<WeeklyMenu, Long> {

    private final WeeklyMenuRepository weeklyMenuRepository;
    private final WorkingWeekService workingWeekService;
    private final FoodService foodService;
    private final FoodOrderRepository foodOrderRepository;
    private final EntityMapper entityMapper;
    private final WeekDayRepository weekDayRepository;

    @Override
    protected BaseRepository<WeeklyMenu, Long> getRepository() {
        return weeklyMenuRepository;
    }

    @Override
    protected String getResourceName() {
        return "Weekly menu";
    }

    public PagedResponse<WeeklyMenuResponse> findAll(WeeklyMenuFilterDTO filter) {

        var pageable = toPageable(filter);

        var page =
                filter.getWeekId() != null && filter.getWeekdayId() != null
                        ? weeklyMenuRepository.findByWorkingWeekIdAndWeekDayId(
                        filter.getWeekId(), filter.getWeekdayId(), pageable)
                        : filter.getWeekId() != null
                          ? weeklyMenuRepository.findByWorkingWeekId(
                        filter.getWeekId(), pageable)
                          : weeklyMenuRepository.findAll(pageable);

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

        WeeklyMenu weeklyMenu = entityMapper.toWeeklyMenu(request);

        weeklyMenu.setWorkingWeek(
                workingWeekService.findEntityById(request.getWeekId()));

        weeklyMenu.setFood(
                foodService.findEntityById(request.getFoodId()));

        weeklyMenu.setWeekDay(
                weekDayRepository.findById(request.getWeekdayId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Week day not found with id: " + request.getWeekdayId())));

        return entityMapper.toWeeklyMenuResponse(
                weeklyMenuRepository.save(weeklyMenu));
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