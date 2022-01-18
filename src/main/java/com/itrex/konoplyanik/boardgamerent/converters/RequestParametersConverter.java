package com.itrex.konoplyanik.boardgamerent.converters;

import com.itrex.konoplyanik.boardgamerent.dto.report.RequestParametersDTO;
import com.itrex.konoplyanik.boardgamerent.entity.report.RequestParameters;

public class RequestParametersConverter {

    public static RequestParameters convertFromDTO(RequestParametersDTO requestParameters) {
        return RequestParameters.builder()
                .start(requestParameters.getStart())
                .end(requestParameters.getEnd())
                .pageNumber(requestParameters.getPageNumber())
                .pageSize(requestParameters.getPageSize())
                .parameter(requestParameters.getParameter())
                .direction(requestParameters.getDirection())
                .build();
    }

}
