package com.itrex.konoplyanik.boardgamerent.service.validator;

import com.itrex.konoplyanik.boardgamerent.dto.report.RequestParametersDTO;

public interface Validator {

    void validate(RequestParametersDTO requestParameters);

}
