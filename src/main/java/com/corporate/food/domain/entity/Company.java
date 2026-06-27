package com.corporate.food.domain.entity;

import com.corporate.food.domain.BaseDomain;
import com.corporate.food.domain.enums.CompanyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company extends BaseDomain {

    @Column(nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type", length = 50)
    private CompanyType companyType;

    @ManyToOne
    @JoinColumn(name = "parent_company_id")
    private Company parentCompany;

    @OneToMany(mappedBy = "parentCompany")
    private List<Company> subsidiaries = new ArrayList<>();

    @OneToMany(mappedBy = "company")
    private List<Employee> employees = new ArrayList<>();
}
