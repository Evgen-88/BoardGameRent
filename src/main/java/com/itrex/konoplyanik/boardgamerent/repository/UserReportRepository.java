package com.itrex.konoplyanik.boardgamerent.repository;

import com.itrex.konoplyanik.boardgamerent.entity.report.PageParameters;
import com.itrex.konoplyanik.boardgamerent.entity.report.UserReport;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;

import java.time.LocalDate;
import java.util.List;

public interface UserReportRepository {

    List<UserReport> getUserReport(LocalDate start, LocalDate end, PageParameters pageParameters) throws RepositoryException;

}
