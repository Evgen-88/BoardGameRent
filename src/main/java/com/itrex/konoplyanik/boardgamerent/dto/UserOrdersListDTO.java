package com.itrex.konoplyanik.boardgamerent.dto;

import java.time.LocalDate;

import com.itrex.konoplyanik.boardgamerent.entity.Status;

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
public class UserOrdersListDTO {

	private Long id;
	private Integer totalPrice;
	private LocalDate date;
	private Status status;
}
