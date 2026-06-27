package com.corporate.food.dto;

import com.corporate.food.domain.enums.EmployeeRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRequest {

    @NotBlank
    @Size(max = 100)
    private String username;

    @Size(min = 6, max = 100)
    private String password;

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Size(max = 50)
    private String personnelCode;

    @NotNull
    private EmployeeRole role;

    private Boolean enabled = true;

    @NotNull
    private Long companyId;
}
