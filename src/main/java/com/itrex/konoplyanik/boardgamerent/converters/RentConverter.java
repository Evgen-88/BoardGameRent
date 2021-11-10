package com.itrex.konoplyanik.boardgamerent.converters;

import java.util.ArrayList;
import java.util.List;

import com.itrex.konoplyanik.boardgamerent.dto.RentDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RentSaveDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;

public class RentConverter {

	public static List<RentDTO> convertRentsToDTO(List<Rent> rents) {
		List<RentDTO> rentsDTO = new ArrayList<>();
		for (Rent rent : rents) {
			rentsDTO.add(convertRentToDTO(rent));
		}
		return rentsDTO;
	}

	public static Rent convertRentSaveToEntity(RentSaveDTO rentDTO) {
		Rent rent = new Rent();
		rent.setId(rentDTO.getId());
		rent.setBoardGameId(rentDTO.getBoardGameId());
		rent.setOrderId(rentDTO.getOrderId());
		rent.setRentFrom(rentDTO.getRentFrom());
		rent.setRentTo(rentDTO.getRentTo());
		rent.setPrice(rentDTO.getPrice());
		return rent;
	}

	public static RentDTO convertRentToDTO(Rent rent) {
		return RentDTO.builder()
				.id(rent.getId())
				.rentFrom(rent.getRentFrom())
				.rentTo(rent.getRentTo())
				.price(rent.getPrice())
				.boardGameId(rent.getBoardGame().getId())
				.boardGameName(rent.getBoardGame().getName())
				.build();
	}
}
