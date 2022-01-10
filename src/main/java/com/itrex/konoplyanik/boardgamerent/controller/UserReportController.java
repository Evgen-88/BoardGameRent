package com.itrex.konoplyanik.boardgamerent.controller;

import com.itrex.konoplyanik.boardgamerent.dto.UserReportDTO;
import com.itrex.konoplyanik.boardgamerent.entity.report.PageParameters;
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
    private static final String DEFAULT_PARAMETER = "summ";
    private static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.DESC;
    private final UserReportService reportService;

    public UserReportController(UserReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<UserReportDTO> getUserReport(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
            @RequestParam(name = "pageNumber", required = false) Integer page,
            @RequestParam(name = "pageSize", required = false) Integer size,
            @RequestParam(name = "parameter", required = false) String sortBy,
            @RequestParam(name = "direction", required = false) String sortDirection
    ) throws ServiceException {
        LocalDate startFrom = LocalDate.ofInstant(start.toInstant(), ZoneId.systemDefault());
        LocalDate endTo = LocalDate.ofInstant(end.toInstant(), ZoneId.systemDefault());
        return reportService.getUserReport(startFrom, endTo, setPageParameters(page, size, sortBy, sortDirection));
    }

    private PageParameters setPageParameters(Integer pageNumber, Integer pageSize, String parameter, String direction) {
        return PageParameters.builder()
                .pageNumber(pageNumber == null || pageNumber < 1 ? 0 : (pageNumber - 1) * pageSize)
                .pageSize(pageSize == null || pageSize < 0 ? DEFAULT_PAGE_SIZE : pageSize)
                .parameter(parameter == null ? DEFAULT_PARAMETER : parameter)
                .direction(direction == null ? DEFAULT_DIRECTION : Sort.Direction.valueOf(direction.toUpperCase(Locale.ROOT)))
                .build();
    }

}
