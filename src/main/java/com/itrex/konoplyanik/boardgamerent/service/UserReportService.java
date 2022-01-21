package com.itrex.konoplyanik.boardgamerent.service;

import com.itrex.konoplyanik.boardgamerent.dto.report.RequestParametersDTO;
import com.itrex.konoplyanik.boardgamerent.dto.report.UserReportDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

import java.util.List;

public interface UserReportService {

    List<UserReportDTO> getUserReport(RequestParametersDTO requestParameters) throws ServiceException;

}
