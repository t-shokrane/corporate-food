package com.corporate.food.dto.filter;

import com.corporate.food.domain.enums.CompanyType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompanyFilterDTO extends BaseFilterDTO {

    private String name;

    private CompanyType companyType;
}
