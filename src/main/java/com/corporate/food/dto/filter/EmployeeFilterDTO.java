package com.corporate.food.dto.filter;

import com.corporate.food.domain.enums.EmployeeRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmployeeFilterDTO extends BaseFilterDTO {

    private Long companyId;

    private String username;

    private String firstName;

    private String lastName;

    private String personnelCode;

    private EmployeeRole role;

    private Boolean enabled;
}