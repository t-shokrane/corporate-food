package com.corporate.food.service;

import com.corporate.food.domain.entity.Employee;
import com.corporate.food.dto.EmployeeRequest;
import com.corporate.food.dto.EmployeeResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.EmployeeFilterDTO;
import com.corporate.food.mapper.EntityMapper;
import com.corporate.food.repository.BaseRepository;
import com.corporate.food.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService extends BaseService<Employee, Long> {

    private final EmployeeRepository employeeRepository;
    private final CompanyService companyService;
    private final EntityMapper entityMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected BaseRepository<Employee, Long> getRepository() {
        return employeeRepository;
    }

    @Override
    protected String getResourceName() {
        return "Employee";
    }

    public PagedResponse<EmployeeResponse> findAll(EmployeeFilterDTO filter) {
        // TODO: Implement business logic using toPageable(filter)
        return PagedResponse.empty(filter);
    }

    public EmployeeResponse findById(Long id) {
        // TODO: Implement business logic
        return null;
    }

    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        // TODO: Implement business logic
        return null;
    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        // TODO: Implement business logic
        return null;
    }

    @Transactional
    public void delete(Long id) {
        // TODO: Implement business logic
    }
}
