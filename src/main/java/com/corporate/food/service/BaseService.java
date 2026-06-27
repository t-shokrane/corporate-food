package com.corporate.food.service;

import com.corporate.food.domain.BaseDomain;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.dto.filter.BaseFilterDTO;
import com.corporate.food.exception.ResourceNotFoundException;
import com.corporate.food.repository.BaseRepository;
import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseService<E extends BaseDomain, ID> {

    protected abstract BaseRepository<E, ID> getRepository();

    protected abstract String getResourceName();

    protected E findEntityById(ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getResourceName() + " not found: " + id));
    }

    protected Pageable toPageable(BaseFilterDTO filter) {
        Sort sort = Sort.by(
                filter.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC,
                filter.getSortBy());
        return PageRequest.of(filter.getPage(), filter.getSize(), sort);
    }

    protected <R> PagedResponse<R> toPagedResponse(Page<E> page, Function<E, R> mapper) {
        return PagedResponse.of(page, page.getContent().stream().map(mapper).toList());
    }

    @Transactional
    public void deleteById(ID id) {
        getRepository().delete(findEntityById(id));
    }
}
