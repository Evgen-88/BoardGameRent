package com.itrex.konoplyanik.boardgamerent.converters;

import com.itrex.konoplyanik.boardgamerent.dto.report.RequestParametersDTO;
import com.itrex.konoplyanik.boardgamerent.entity.report.RequestParameters;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

public class RequestParametersConverter {

    private static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final String DEFAULT_PARAMETER = "sum";
    public static final String QUANTITY_PARAMETER = "quantity";
    public static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.DESC;

    public static RequestParameters convertFromDTO(RequestParametersDTO requestParameters) {
        LocalDate start = LocalDate.ofInstant(requestParameters.getStart().toInstant(), ZoneId.systemDefault());
        LocalDate end = LocalDate.ofInstant(requestParameters.getEnd().toInstant(), ZoneId.systemDefault());
        long pageSize = requestParameters.getPageSize() == null ? DEFAULT_PAGE_SIZE : requestParameters.getPageSize();
        long pageNumber = requestParameters.getPageNumber() == null ? 0 : (requestParameters.getPageNumber() - 1) * pageSize;
        String parameter = requestParameters.getParameter() == null ? DEFAULT_PARAMETER : requestParameters.getParameter();
        Sort.Direction direction = requestParameters.getDirection() == null ? DEFAULT_DIRECTION
                : Sort.Direction.valueOf(requestParameters.getDirection().toUpperCase(Locale.ROOT));
        return RequestParameters.builder()
                .start(start)
                .end(end)
                .pageSize(pageSize)
                .pageNumber(pageNumber)
                .parameter(parameter)
                .direction(direction)
                .build();
    }

}
