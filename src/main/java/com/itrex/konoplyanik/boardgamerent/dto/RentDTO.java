package com.itrex.konoplyanik.boardgamerent.dto;

import java.time.LocalDate;

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
public class RentDTO {

	private Long id;
	private LocalDate rentFrom;
	private LocalDate rentTo;
	private Integer price;
	private Long boardGameId;
	private String boardGameName;
}
