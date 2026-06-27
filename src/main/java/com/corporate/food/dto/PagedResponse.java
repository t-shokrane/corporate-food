package com.corporate.food.dto;

import com.corporate.food.dto.filter.BaseFilterDTO;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Page;

@Value
@Builder
public class PagedResponse<T> {

    List<T> items;
    int page;
    int size;
    long totalElements;
    int totalPages;

    public static <T> PagedResponse<T> of(Page<?> page, List<T> items) {
        return PagedResponse.<T>builder()
                .items(items)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    public static <T> PagedResponse<T> empty(BaseFilterDTO filter) {
        return PagedResponse.<T>builder()
                .items(List.of())
                .page(filter.getPage())
                .size(filter.getSize())
                .totalElements(0)
                .totalPages(0)
                .build();
    }
}
