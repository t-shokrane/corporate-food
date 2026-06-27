package com.corporate.food.dto.filter;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseFilterDTO implements Serializable {

    /**
     * Page number (0-indexed)
     * Default: 0
     */
    @Min(value = 0, message = "Page number must be non-negative")
    private Integer page = 0;

    /**
     * Page size (number of items per page)
     * Default: 10
     * Max: 100
     */
    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size must not exceed 100")
    private Integer size = 10;

    /**
     * Sort field name
     * Default: "id"
     */
    private String sortBy = "id";

    /**
     * Sort direction: ASC or DESC
     * Default: "ASC"
     */
    private String sortDirection = "ASC";

    /**
     * Get sort direction as boolean
     * @return true for ASC, false for DESC
     */
    public boolean isAscending() {
        return !"DESC".equalsIgnoreCase(sortDirection);
    }
}
