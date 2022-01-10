package com.itrex.konoplyanik.boardgamerent.entity.report;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
@Builder
public class PageParameters {

    private int pageNumber;
    private int pageSize;
    private String parameter;
    private Sort.Direction direction;

}
