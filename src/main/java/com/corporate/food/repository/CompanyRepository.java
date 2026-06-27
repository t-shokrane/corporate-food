package com.corporate.food.repository;

import com.corporate.food.domain.entity.Company;
import com.corporate.food.domain.enums.CompanyType;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyRepository extends BaseRepository<Company, Long> {

    List<Company> findByCompanyType(CompanyType companyType);

    List<Company> findByParentCompanyId(Long parentCompanyId);

    Page<Company> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Company> findByCompanyType(CompanyType companyType, Pageable pageable);

    Page<Company> findByNameContainingIgnoreCaseAndCompanyType(
            String name, CompanyType companyType, Pageable pageable);
}
