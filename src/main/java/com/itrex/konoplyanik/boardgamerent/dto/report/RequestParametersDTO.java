package com.itrex.konoplyanik.boardgamerent.dto.report;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
public class RequestParametersDTO {

    private @DateTimeFormat(pattern = "yyyy-MM-dd") Date start;
    private @DateTimeFormat(pattern = "yyyy-MM-dd") Date end;
    private Long pageNumber;
    private Long pageSize;
    private String parameter;
    private String direction;

}
