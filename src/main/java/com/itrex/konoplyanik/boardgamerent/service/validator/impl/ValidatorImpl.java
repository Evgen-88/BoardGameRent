package com.itrex.konoplyanik.boardgamerent.service.validator.impl;

import com.itrex.konoplyanik.boardgamerent.dto.report.RequestParametersDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.service.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.itrex.konoplyanik.boardgamerent.converters.RequestParametersConverter.*;

@Component
public class ValidatorImpl implements Validator {

    private static final String ASC_SORT_DIRECTION = "ASC";

    @Override
    public void validate(RequestParametersDTO requestParameters) throws ServiceException {
        validatePeriod(requestParameters.getStart(), requestParameters.getEnd());
        validatePageNumber(requestParameters.getPageNumber());
        validatePageSize(requestParameters.getPageSize());
        validateParameter(requestParameters.getParameter());
        validateDirection(requestParameters.getDirection());
    }

    private void validatePeriod(Date start, Date end) {
        if (start.after(end)) {
            throw new ServiceException("Wrong dates");
        }
    }

    private void validatePageNumber(Long pageNumber) {
        if (pageNumber != null && pageNumber < 0) {
            throw new ServiceException("Page number should be greater then 0");
        }
    }

    private void validatePageSize(Long pageSize) {
        if (pageSize != null && pageSize < 1) {
            throw new ServiceException("Page size should be greater than 0");
        }
    }

    private void validateParameter(String parameter) {
        if (parameter != null && !parameter.equals(QUANTITY_PARAMETER) && !parameter.equals(DEFAULT_PARAMETER)) {
            throw new ServiceException("Unknown sorting parameter");
        }
    }

    private void validateDirection(String direction) {
        if (direction != null && !direction.equalsIgnoreCase(ASC_SORT_DIRECTION) && !direction.equalsIgnoreCase(DEFAULT_DIRECTION.name())) {
            throw new ServiceException("Invalid sorting direction");
        }
    }

}
