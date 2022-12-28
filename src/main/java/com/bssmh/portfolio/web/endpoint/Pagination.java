package com.bssmh.portfolio.web.endpoint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {
    private Integer page = 1;
    private Integer size = 10;
    private Long totalCount;
    private Integer totalPages;

    public static Pagination create(Integer page, Integer pageSize) {
        return Pagination.builder()
                .page(page + 1)
                .size(pageSize)
                .build();
    }

    public static Pagination create(Integer page, Integer pageSize, Long totalCount, Integer totalPages) {
        return Pagination.builder()
                .page(page + 1)
                .size(pageSize)
                .totalCount(totalCount)
                .totalPages(totalPages)
                .build();
    }

    public static <T> Pagination create(Page<T> page) {
        return Pagination.builder()
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalCount(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
