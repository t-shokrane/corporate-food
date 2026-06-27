package com.corporate.food.mapper;

import com.corporate.food.domain.entity.Company;
import com.corporate.food.domain.entity.Employee;
import com.corporate.food.domain.entity.Food;
import com.corporate.food.domain.entity.FoodOrder;
import com.corporate.food.domain.entity.WeekDay;
import com.corporate.food.domain.entity.WeeklyMenu;
import com.corporate.food.domain.entity.WorkingWeek;
import com.corporate.food.dto.CompanyResponse;
import com.corporate.food.dto.EmployeeResponse;
import com.corporate.food.dto.FoodOrderResponse;
import com.corporate.food.dto.FoodResponse;
import com.corporate.food.dto.WeekDayResponse;
import com.corporate.food.dto.WeeklyMenuResponse;
import com.corporate.food.dto.WorkingWeekResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

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

    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "companyName", source = "company.name")
    EmployeeResponse toEmployeeResponse(Employee employee);

    FoodResponse toFoodResponse(Food food);

    WorkingWeekResponse toWorkingWeekResponse(WorkingWeek workingWeek);

    WeekDayResponse toWeekDayResponse(WeekDay weekDay);

    @Mapping(target = "weekId", source = "workingWeek.id")
    @Mapping(target = "weekdayId", source = "weekDay.id")
    @Mapping(target = "weekdayPersianName", source = "weekDay.persianName")
    @Mapping(target = "foodId", source = "food.id")
    @Mapping(target = "foodName", source = "food.name")
    @Mapping(target = "registeredOrderCount", ignore = true)
    WeeklyMenuResponse toWeeklyMenuResponse(WeeklyMenu weeklyMenu);

    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", source = "employee", qualifiedByName = "employeeFullName")
    @Mapping(target = "weekId", source = "workingWeek.id")
    @Mapping(target = "weekdayId", source = "weekDay.id")
    @Mapping(target = "weekdayPersianName", source = "weekDay.persianName")
    @Mapping(target = "foodId", source = "food.id")
    @Mapping(target = "foodName", source = "food.name")
    FoodOrderResponse toFoodOrderResponse(FoodOrder order);
}
