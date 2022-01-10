package com.itrex.konoplyanik.boardgamerent.service.impl;

import com.itrex.konoplyanik.boardgamerent.converters.UserReportConverter;
import com.itrex.konoplyanik.boardgamerent.dto.UserReportDTO;
import com.itrex.konoplyanik.boardgamerent.entity.report.PageParameters;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.UserReportRepository;
import com.itrex.konoplyanik.boardgamerent.service.UserReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UserReportServiceImpl implements UserReportService {

    private final UserReportRepository reportRepository;

    @Override
    public List<UserReportDTO> getUserReport(LocalDate start, LocalDate end, PageParameters pageParameters) throws ServiceException {
        try {
            return UserReportConverter.convertToListDTO(reportRepository.getUserReport(start, end, pageParameters));
        } catch (RepositoryException ex) {
            throw new RepositoryException(ex.getMessage());
        }
    }
}
