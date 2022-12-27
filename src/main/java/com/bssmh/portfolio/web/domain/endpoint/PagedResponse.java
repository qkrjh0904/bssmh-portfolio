package com.bssmh.portfolio.web.domain.endpoint;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonSerialize
public class PagedResponse<T> {

    private Pagination pagination;
    private List<T> list;

    public static <T> PagedResponse<T> create(Pagination pagination, List<T> list) {
        return new PagedResponse<>(pagination, list);
    }

    public static <T> PagedResponse<T> create(Page<T> page) {
        return new PagedResponse<>(Pagination.create(page), page.getContent());
    }
}
