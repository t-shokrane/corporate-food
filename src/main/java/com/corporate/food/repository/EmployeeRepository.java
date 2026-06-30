package com.corporate.food.repository;

import com.corporate.food.domain.entity.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface EmployeeRepository extends BaseRepository<Employee, Long> {

    Optional<Employee> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByPersonnelCode(String personnelCode);

    List<Employee> findByCompanyId(Long companyId);
    Page<Employee> findByCompanyId(Long companyId, Pageable pageable);
}
