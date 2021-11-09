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
public class BoardGameDTO {

	private Long id;
	private String name;
	private Integer rentPrice;
	private Integer quantity;
	private Integer availableQuantity;
}
