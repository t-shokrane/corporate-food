package com.corporate.food.dto;

import com.corporate.food.domain.enums.EmployeeRole;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmployeeResponse {

    Long id;
    String username;
    String firstName;
    String lastName;
    String personnelCode;
    EmployeeRole role;
    Boolean enabled;
    Long companyId;
    String companyName;
    LocalDateTime createdOn;
    LocalDateTime updatedOn;
}
