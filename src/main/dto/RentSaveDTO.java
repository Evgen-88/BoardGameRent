package com.itrex.konoplyanik.boardgamerent.dto;

import java.time.LocalDate;

import com.itrex.konoplyanik.boardgamerent.entity.BoardGame;
import com.itrex.konoplyanik.boardgamerent.entity.Order;

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
public class RentSaveDTO {

	private Long id;
	private BoardGame boardGame;
	private Order order;
	private LocalDate rentFrom;
	private LocalDate rentTo;
	private Integer price;
}
