package com.corporate.food.service;

import com.corporate.food.domain.entity.Company;
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
        // TODO: Filter not applied — filter.companyId is ignored; repository uses findAll without company predicate
        var pageable = toPageable(filter);
        var page = employeeRepository.findAll(pageable);

        // TODO: GET list returns 500 — lazy-loaded company accessed during toEmployeeResponse mapping; null/soft-deleted company causes NPE
        var items = page.getContent()
                .stream()
                .map(entityMapper::toEmployeeResponse)
                .toList();

        return PagedResponse.of(page, items);

    }

    public EmployeeResponse findById(Long id) {
        return entityMapper.toEmployeeResponse(findEntityById(id));

    }

    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        Company company = companyService.findEntityById(request.getCompanyId());

        Employee employee = new Employee();
        employee.setUsername(request.getUsername());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setPersonnelCode(request.getPersonnelCode());
        employee.setRole(request.getRole());
        employee.setEnabled(true);
        employee.setCompany(company);

        Employee saved = employeeRepository.save(employee);

        return entityMapper.toEmployeeResponse(saved);

    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = findEntityById(id);

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setPersonnelCode(request.getPersonnelCode());
        employee.setRole(request.getRole());

        if (request.getPassword() != null) {
            employee.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Employee updated = employeeRepository.save(employee);

        return entityMapper.toEmployeeResponse(updated);

    }

    @Transactional
    public void delete(Long id) {
        deleteById(id);
    }
}
