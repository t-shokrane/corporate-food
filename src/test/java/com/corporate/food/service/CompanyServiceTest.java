package com.corporate.food.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.corporate.food.domain.entity.Company;
import com.corporate.food.domain.enums.CompanyType;
import com.corporate.food.dto.CompanyRequest;
import com.corporate.food.dto.CompanyResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.CompanyFilterDTO;
import com.corporate.food.exception.BusinessException;
import com.corporate.food.exception.ResourceNotFoundException;
import com.corporate.food.mapper.EntityMapper;
import com.corporate.food.repository.CompanyRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private EntityMapper entityMapper;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void findById_returnsMappedResponse() {
        Company company = company(1L, "گروه الین", CompanyType.HOLDING, null);
        CompanyResponse response = companyResponse(1L, "گروه الین", CompanyType.HOLDING, null);

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(entityMapper.toCompanyResponse(company)).thenReturn(response);

        CompanyResponse result = companyService.findById(1L);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void findById_whenMissing_throwsResourceNotFound() {
        when(companyRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> companyService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Company not found: 99");
    }

    @Test
    void findAll_withNameFilter_queriesByName() {
        CompanyFilterDTO filter = new CompanyFilterDTO();
        filter.setName("الین");

        Company company = company(1L, "گروه الین", CompanyType.HOLDING, null);
        Page<Company> page = new PageImpl<>(List.of(company));
        CompanyResponse response = companyResponse(1L, "گروه الین", CompanyType.HOLDING, null);

        when(companyRepository.findByNameContainingIgnoreCase(eq("الین"), any(Pageable.class))).thenReturn(page);
        when(entityMapper.toCompanyResponse(company)).thenReturn(response);

        PagedResponse<CompanyResponse> result = companyService.findAll(filter);

        assertThat(result.getItems()).containsExactly(response);
        assertThat(result.getTotalElements()).isEqualTo(1);
        verify(companyRepository).findByNameContainingIgnoreCase(eq("الین"), any(Pageable.class));
        verify(companyRepository, never()).findAll(any(Pageable.class));
    }

    @Test
    void findAll_withCompanyTypeFilter_queriesByType() {
        CompanyFilterDTO filter = new CompanyFilterDTO();
        filter.setCompanyType(CompanyType.SUBSIDIARY);

        Company company = company(2L, "شرکت لمون", CompanyType.SUBSIDIARY, 1L);
        Page<Company> page = new PageImpl<>(List.of(company));
        CompanyResponse response = companyResponse(2L, "شرکت لمون", CompanyType.SUBSIDIARY, 1L);

        when(companyRepository.findByCompanyType(eq(CompanyType.SUBSIDIARY), any(Pageable.class))).thenReturn(page);
        when(entityMapper.toCompanyResponse(company)).thenReturn(response);

        PagedResponse<CompanyResponse> result = companyService.findAll(filter);

        assertThat(result.getItems()).containsExactly(response);
        verify(companyRepository).findByCompanyType(eq(CompanyType.SUBSIDIARY), any(Pageable.class));
    }

    @Test
    void create_withoutParent_savesCompany() {
        CompanyRequest request = new CompanyRequest();
        request.setName("شرکت جدید");
        request.setCompanyType(CompanyType.SUBSIDIARY);

        Company saved = company(5L, "شرکت جدید", CompanyType.SUBSIDIARY, null);
        CompanyResponse response = companyResponse(5L, "شرکت جدید", CompanyType.SUBSIDIARY, null);

        when(companyRepository.save(any(Company.class))).thenReturn(saved);
        when(entityMapper.toCompanyResponse(saved)).thenReturn(response);

        CompanyResponse result = companyService.create(request);

        assertThat(result).isEqualTo(response);

        ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);
        verify(companyRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("شرکت جدید");
        assertThat(captor.getValue().getCompanyType()).isEqualTo(CompanyType.SUBSIDIARY);
        assertThat(captor.getValue().getParentCompany()).isNull();
    }

    @Test
    void create_withParent_setsParentCompany() {
        CompanyRequest request = new CompanyRequest();
        request.setName("شرکت جدید");
        request.setCompanyType(CompanyType.SUBSIDIARY);
        request.setParentCompanyId(1L);

        Company parent = company(1L, "گروه الین", CompanyType.HOLDING, null);
        Company saved = company(5L, "شرکت جدید", CompanyType.SUBSIDIARY, 1L);
        CompanyResponse response = companyResponse(5L, "شرکت جدید", CompanyType.SUBSIDIARY, 1L);

        when(companyRepository.findById(1L)).thenReturn(Optional.of(parent));
        when(companyRepository.save(any(Company.class))).thenReturn(saved);
        when(entityMapper.toCompanyResponse(saved)).thenReturn(response);

        CompanyResponse result = companyService.create(request);

        assertThat(result).isEqualTo(response);

        ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);
        verify(companyRepository).save(captor.capture());
        assertThat(captor.getValue().getParentCompany()).isEqualTo(parent);
    }

    @Test
    void update_whenSelfParent_throwsBusinessException() {
        CompanyRequest request = new CompanyRequest();
        request.setName("گروه الین");
        request.setCompanyType(CompanyType.HOLDING);
        request.setParentCompanyId(1L);

        Company company = company(1L, "گروه الین", CompanyType.HOLDING, null);
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        assertThatThrownBy(() -> companyService.update(1L, request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Company cannot be its own parent");

        verify(companyRepository, never()).save(any());
    }

    @Test
    void update_whenParentNull_clearsParentCompany() {
        CompanyRequest request = new CompanyRequest();
        request.setName("گروه الین");
        request.setCompanyType(CompanyType.HOLDING);

        Company company = company(2L, "شرکت لمون", CompanyType.SUBSIDIARY, 1L);
        Company saved = company(2L, "گروه الین", CompanyType.HOLDING, null);
        CompanyResponse response = companyResponse(2L, "گروه الین", CompanyType.HOLDING, null);

        when(companyRepository.findById(2L)).thenReturn(Optional.of(company));
        when(companyRepository.save(company)).thenReturn(saved);
        when(entityMapper.toCompanyResponse(saved)).thenReturn(response);

        CompanyResponse result = companyService.update(2L, request);

        assertThat(result).isEqualTo(response);
        assertThat(company.getParentCompany()).isNull();
    }

    @Test
    void delete_removesCompany() {
        Company company = company(1L, "گروه الین", CompanyType.HOLDING, null);
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        companyService.delete(1L);

        verify(companyRepository).delete(company);
    }

    private Company company(Long id, String name, CompanyType type, Long parentId) {
        Company company = new Company();
        company.setId(id);
        company.setName(name);
        company.setCompanyType(type);
        if (parentId != null) {
            Company parent = new Company();
            parent.setId(parentId);
            company.setParentCompany(parent);
        }
        return company;
    }

    private CompanyResponse companyResponse(Long id, String name, CompanyType type, Long parentId) {
        return CompanyResponse.builder()
                .id(id)
                .name(name)
                .companyType(type)
                .parentCompanyId(parentId)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }
}
