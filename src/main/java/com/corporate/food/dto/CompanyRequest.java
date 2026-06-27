package com.corporate.food.dto;

import com.corporate.food.domain.enums.CompanyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CompanyRequest {

    @NotBlank
    @Size(max = 150)
    private String name;

    private CompanyType companyType;

    private Long parentCompanyId;
}
