package com.itrex.konoplyanik.boardgamerent.converters;

import com.itrex.konoplyanik.boardgamerent.dto.UserReportDTO;
import com.itrex.konoplyanik.boardgamerent.entity.report.UserReport;

import java.util.List;
import java.util.stream.Collectors;

public class UserReportConverter {

    public static UserReportDTO convertToDTO(UserReport userReport) {
        return UserReportDTO.builder()
                .id(userReport.getId())
                .login(userReport.getLogin())
                .orderQuantity(userReport.getOrderQuantity())
                .orderSum(userReport.getOrderSum())
                .purchaseQuantity(userReport.getPurchaseQuantity())
                .purchaseSum(userReport.getPurchaseSum())
                .rentQuantity(userReport.getRentQuantity())
                .rentSum(userReport.getRentSum())
                .build();
    }

    public static List<UserReportDTO> convertToListDTO(List<UserReport> userReports) {
        return userReports.stream()
                .map(UserReportConverter::convertToDTO)
                .collect(Collectors.toList());
    }

}
