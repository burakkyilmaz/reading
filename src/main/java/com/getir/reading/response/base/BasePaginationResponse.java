package com.getir.reading.response.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePaginationResponse extends BaseResponse {
    private Integer page;
    private Integer limit;
    private Long count;
}
