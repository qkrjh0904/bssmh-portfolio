package com.bssmh.portfolio.web.endpoint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;


@Getter
@NoArgsConstructor
public class Pagination {
    private Integer page = 1;
    private Integer size = 10;
    private Long totalCount;
    private Integer totalPages;


    public PageRequest toPageRequest() {
        validationCheck();
        return PageRequest.of(this.page - 1, this.size);
    }

    private void validationCheck() {
        if (this.page < 1) {
            this.page = 1;
        }

        this.size = 10;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
