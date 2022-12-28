package com.bssmh.portfolio.web.endpoint;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListResponse<T> {

    private List<T> list;

}
