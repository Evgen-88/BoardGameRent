package com.itrex.konoplyanik.boardgamerent.controller;

import com.itrex.konoplyanik.boardgamerent.dto.report.RequestParametersDTO;
import com.itrex.konoplyanik.boardgamerent.dto.report.UserReportDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.service.UserReportService;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/reports")
public class UserReportController {

    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final String DEFAULT_PARAMETER = "sum";
    private static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.DESC;
    private final UserReportService reportService;

    public UserReportController(UserReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<UserReportDTO> getUserReport(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
            @RequestParam(name = "pageNumber", defaultValue  = "1") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue  = "10") Integer pageSize,
            @RequestParam(name = "parameter", required = false) String parameter,
            @RequestParam(name = "direction", required = false) String direction
    ) throws ServiceException {
        return reportService.getUserReport(setRequestParameters(start, end, pageNumber, pageSize, parameter, direction));
    }

    private RequestParametersDTO setRequestParameters(Date start, Date end, Integer pageNumber, Integer pageSize, String parameter, String direction) {
        return RequestParametersDTO.builder()
                .start(LocalDate.ofInstant(start.toInstant(), ZoneId.systemDefault()))
                .end(LocalDate.ofInstant(end.toInstant(), ZoneId.systemDefault()))
                .pageSize(pageSize < 0 ? DEFAULT_PAGE_SIZE : pageSize)
                .pageNumber(pageNumber < 1 ? 0 : (pageNumber - 1) * pageSize)
                .parameter(parameter == null ? DEFAULT_PARAMETER : parameter)
                .direction(direction == null ? DEFAULT_DIRECTION : Sort.Direction.valueOf(direction.toUpperCase(Locale.ROOT)))
                .build();
    }

}
