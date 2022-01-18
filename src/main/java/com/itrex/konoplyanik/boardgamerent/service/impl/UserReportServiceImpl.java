package com.itrex.konoplyanik.boardgamerent.service.impl;

import com.itrex.konoplyanik.boardgamerent.converters.RequestParametersConverter;
import com.itrex.konoplyanik.boardgamerent.converters.UserReportConverter;
import com.itrex.konoplyanik.boardgamerent.dto.report.RequestParametersDTO;
import com.itrex.konoplyanik.boardgamerent.dto.report.UserReportDTO;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.UserReportRepository;
import com.itrex.konoplyanik.boardgamerent.service.UserReportService;
import com.itrex.konoplyanik.boardgamerent.service.validator.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserReportServiceImpl implements UserReportService {

    private final UserReportRepository reportRepository;
    private final Validator validator;

    @Override
    public List<UserReportDTO> getUserReport(RequestParametersDTO requestParameters) throws ServiceException {
        validator.validate(requestParameters);
        try {
            return UserReportConverter.convertToListDTO(reportRepository
                    .getUserReport(RequestParametersConverter.convertFromDTO(requestParameters)));
        } catch (RepositoryException ex) {
            throw new RepositoryException(ex.getMessage());
        }
    }

}
