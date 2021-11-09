package com.itrex.konoplyanik.boardgamerent.dto;

import java.time.LocalDate;
import java.util.List;
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
public class OrderDTO {

	private Long userId;
	private Long id;
	private Integer totalPrice;
	private LocalDate date;
	private Status status;
	private UserBaseDTO user;
	private List<PurchaseDTO> purchases;
	private List<RentDTO> rents;
}
