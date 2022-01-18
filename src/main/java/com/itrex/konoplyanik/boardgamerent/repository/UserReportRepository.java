package com.itrex.konoplyanik.boardgamerent.repository;

import com.itrex.konoplyanik.boardgamerent.entity.report.RequestParameters;
import com.itrex.konoplyanik.boardgamerent.entity.report.UserReport;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;

import java.util.List;

public interface UserReportRepository {

    List<UserReport> getUserReport(RequestParameters requestParameters) throws RepositoryException;

}
