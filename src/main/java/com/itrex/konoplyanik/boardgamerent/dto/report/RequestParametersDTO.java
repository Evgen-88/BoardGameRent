package com.itrex.konoplyanik.boardgamerent.dto.report;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@Data
@Builder
public class RequestParametersDTO {

    private LocalDate start;
    private LocalDate end;
    private int pageNumber;
    private int pageSize;
    private String parameter;
    private Sort.Direction direction;

}
