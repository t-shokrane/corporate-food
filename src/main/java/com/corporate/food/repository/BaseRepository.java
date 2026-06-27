package com.corporate.food.repository;

import com.corporate.food.domain.BaseDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseDomain, ID> extends JpaRepository<T, ID> {
}
