package com.bssmh.portfolio.web.endpoint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.Objects;


@Getter
@NoArgsConstructor
public class Pagination {
    private Integer page;
    private Integer size;
    private Long totalCount;
    private Integer totalPages;


    public PageRequest toPageRequest() {
        validationCheck();
        return PageRequest.of(this.page - 1, this.size);
    }

    private void validationCheck() {
        if (Objects.isNull(page)) {
            page = 1;
        }

        if (Objects.isNull(size)) {
            size = 10;
        }

        if (this.page < 1) {
            this.page = 1;
        }

        if(size>100){
            this.size = 100;
        }
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
