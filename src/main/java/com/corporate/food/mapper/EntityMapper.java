package com.corporate.food.mapper;

import com.corporate.food.domain.entity.Company;
import com.corporate.food.domain.entity.Employee;
import com.corporate.food.domain.entity.Food;
import com.corporate.food.domain.entity.FoodOrder;
import com.corporate.food.domain.entity.WeekDay;
import com.corporate.food.domain.entity.WeeklyMenu;
import com.corporate.food.domain.entity.WorkingWeek;
import com.corporate.food.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EntityMapper {

    @Named("employeeFullName")
    default String employeeFullName(Employee employee) {
        if (employee == null) {
            return null;
        }
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();
        if (firstName == null && lastName == null) {
            return null;
        }
        if (firstName == null) {
            return lastName;
        }
        if (lastName == null) {
            return firstName;
        }
        return firstName + " " + lastName;
    }

    @Mapping(target = "parentCompanyId", source = "parentCompany.id")
    CompanyResponse toCompanyResponse(Company company);

    // TODO: DTO mapping unsafe with soft-deleted relations — company may be soft-deleted; null-check or fetch active company before mapping id/name
    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "companyName", source = "company.name")
    EmployeeResponse toEmployeeResponse(Employee employee);

    FoodResponse toFoodResponse(Food food);

    WorkingWeekResponse toWorkingWeekResponse(WorkingWeek workingWeek);

    WeekDayResponse toWeekDayResponse(WeekDay weekDay);

    // TODO: DTO mapping unsafe with soft-deleted relations — workingWeek/weekDay/food may be soft-deleted; causes NPE or LazyInitializationException on list mapping
    @Mapping(target = "weekId", source = "workingWeek.id")
    @Mapping(target = "weekdayId", source = "weekDay.id")
    @Mapping(target = "weekdayPersianName", source = "weekDay.persianName")
    @Mapping(target = "foodId", source = "food.id")
    @Mapping(target = "foodName", source = "food.name")
    @Mapping(target = "registeredOrderCount", ignore = true)
    WeeklyMenuResponse toWeeklyMenuResponse(WeeklyMenu weeklyMenu);

    // TODO: DTO mapping unsafe with soft-deleted relations — employee/workingWeek/weekDay/food may be soft-deleted; causes NPE or LazyInitializationException on list mapping
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", source = "employee", qualifiedByName = "employeeFullName")
    @Mapping(target = "weekId", source = "workingWeek.id")
    @Mapping(target = "weekdayId", source = "weekDay.id")
    @Mapping(target = "weekdayPersianName", source = "weekDay.persianName")
    @Mapping(target = "foodId", source = "food.id")
    @Mapping(target = "foodName", source = "food.name")
    FoodOrderResponse toFoodOrderResponse(FoodOrder order);

    void updateWeeklyMenuFromRequest(WeeklyMenuRequest request, @MappingTarget WeeklyMenu weeklyMenu);
    // TODO: POST broken — return type is Object instead of WeeklyMenu; MapStruct cannot map weekId/weekdayId/foodId to entity relations
    WeeklyMenu toWeeklyMenu(WeeklyMenuRequest request);

    // TODO: POST broken — return type is Object instead of Food; callers must unsafe-cast, breaking create flow
    Food toFood(FoodRequest request);

    void updateFoodFromRequest(FoodRequest request, @MappingTarget Food food);

    void updateWorkingWeekFromRequest(WorkingWeekRequest request, @MappingTarget WorkingWeek workingWeek);

    // TODO: POST broken — return type is Object instead of WorkingWeek; callers must unsafe-cast, breaking create flow
    WorkingWeek toWorkingWeek(WorkingWeekRequest request);
}
