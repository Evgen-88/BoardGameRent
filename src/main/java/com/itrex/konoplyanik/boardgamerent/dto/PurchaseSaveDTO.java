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
public class PurchaseSaveDTO {

	private Long id;
	private Long accessoryId;
	private Long orderId;
	private Integer quantity;
	private Integer price;
}
