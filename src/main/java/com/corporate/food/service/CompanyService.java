package com.corporate.food.service;

import com.corporate.food.domain.entity.Company;
import com.corporate.food.dto.CompanyRequest;
import com.corporate.food.dto.CompanyResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.CompanyFilterDTO;
import com.corporate.food.exception.BusinessException;
import com.corporate.food.mapper.EntityMapper;
import com.corporate.food.repository.BaseRepository;
import com.corporate.food.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService extends BaseService<Company, Long> {

    private final CompanyRepository companyRepository;
    private final EntityMapper entityMapper;

    @Override
    protected BaseRepository<Company, Long> getRepository() {
        return companyRepository;
    }

    @Override
    protected String getResourceName() {
        return "Company";
    }

    public PagedResponse<CompanyResponse> findAll(CompanyFilterDTO filter) {
        Pageable pageable = toPageable(filter);
        Page<Company> page = resolveCompanyPage(filter, pageable);
        return toPagedResponse(page, entityMapper::toCompanyResponse);
    }

    private Page<Company> resolveCompanyPage(CompanyFilterDTO filter, Pageable pageable) {
        boolean hasName = filter.getName() != null && !filter.getName().isBlank();
        if (hasName && filter.getCompanyType() != null) {
            return companyRepository.findByNameContainingIgnoreCaseAndCompanyType(
                    filter.getName(), filter.getCompanyType(), pageable);
        }
        if (hasName) {
            return companyRepository.findByNameContainingIgnoreCase(filter.getName(), pageable);
        }
        if (filter.getCompanyType() != null) {
            return companyRepository.findByCompanyType(filter.getCompanyType(), pageable);
        }
        return companyRepository.findAll(pageable);
    }

    public CompanyResponse findById(Long id) {
        return entityMapper.toCompanyResponse(findEntityById(id));
    }

    @Transactional
    public CompanyResponse create(CompanyRequest request) {
        Company company = new Company();
        company.setName(request.getName());
        company.setCompanyType(request.getCompanyType());
        if (request.getParentCompanyId() != null) {
            company.setParentCompany(findEntityById(request.getParentCompanyId()));
        }
        return entityMapper.toCompanyResponse(companyRepository.save(company));
    }

    @Transactional
    public CompanyResponse update(Long id, CompanyRequest request) {
        Company company = findEntityById(id);
        company.setName(request.getName());
        company.setCompanyType(request.getCompanyType());
        if (request.getParentCompanyId() != null) {
            if (request.getParentCompanyId().equals(id)) {
                throw new BusinessException("Company cannot be its own parent");
            }
            company.setParentCompany(findEntityById(request.getParentCompanyId()));
        } else {
            company.setParentCompany(null);
        }
        return entityMapper.toCompanyResponse(companyRepository.save(company));
    }

    @Transactional
    public void delete(Long id) {
        // TODO: Delete integrity — deleting company breaks dependent employees, child companies, and orders; cascade or restrict delete required
        deleteById(id);
    }
}
