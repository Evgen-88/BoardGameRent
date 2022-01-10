package com.itrex.konoplyanik.boardgamerent.service;

import com.itrex.konoplyanik.boardgamerent.dto.UserReportDTO;
import com.itrex.konoplyanik.boardgamerent.entity.report.PageParameters;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

import java.time.LocalDate;
import java.util.List;

public interface UserReportService {

    List<UserReportDTO> getUserReport(LocalDate start, LocalDate end, PageParameters pageParameters) throws ServiceException;

}
