package com.corporate.food.dto;

import com.corporate.food.domain.enums.CompanyType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CompanyResponse {

    Long id;
    String name;
    CompanyType companyType;
    Long parentCompanyId;
    LocalDateTime createdOn;
    LocalDateTime updatedOn;
}
