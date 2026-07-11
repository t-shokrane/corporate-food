package com.corporate.food.service;

import com.corporate.food.domain.entity.Employee;
import com.corporate.food.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final EmployeeRepository employeeRepository;


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {


        Employee employee =
                employeeRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "Employee not found"
                                ));


        return User.builder()
                .username(employee.getUsername())
                .password(employee.getPassword())
                .disabled(!employee.getEnabled())
                .authorities(
                        new SimpleGrantedAuthority(
                                "ROLE_" + employee.getRole().name()
                        )
                )
                .build();
    }
}