package com.bssmh.portfolio.web.endpoint;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class PagedResponse<T> {

    private Pagination pagination;
    private List<T> list;

    public static <T> PagedResponse<T> create(Pagination pagination, List<T> list) {
        return new PagedResponse<>(pagination, list);
    }

}
