package com.itrex.konoplyanik.boardgamerent.controller;

import com.itrex.konoplyanik.boardgamerent.dto.report.RequestParametersDTO;
import com.itrex.konoplyanik.boardgamerent.dto.report.UserReportDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.service.UserReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class UserReportController {

    private final UserReportService reportService;

    @GetMapping
    public List<UserReportDTO> getUserReport(
            @RequestBody RequestParametersDTO requestParameters
    ) throws ServiceException {
        return reportService.getUserReport(requestParameters);
    }

}
