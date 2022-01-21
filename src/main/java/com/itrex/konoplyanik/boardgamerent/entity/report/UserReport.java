package com.itrex.konoplyanik.boardgamerent.entity.report;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserReport {

    private Long id;
    private String login;
    private Long orderQuantity;
    private Long orderSum;
    private Long rentQuantity;
    private Long rentSum;
    private Long purchaseQuantity;
    private Long purchaseSum;

}
