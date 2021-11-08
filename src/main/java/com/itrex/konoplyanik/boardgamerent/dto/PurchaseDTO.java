package com.itrex.konoplyanik.boardgamerent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class PurchaseDTO {

	private Long id;
	private Long orderId;
	private Integer quantity;
	private Integer price;
	private Long accessoryId;
	private String accessoryName;
	
}
