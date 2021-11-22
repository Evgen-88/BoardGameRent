package com.itrex.konoplyanik.boardgamerent.converters;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
		return Rent.builder()
				.id(rentDTO.getId())
				.boardGame(rentDTO.getBoardGame())
				.order(rentDTO.getOrder())
				.rentFrom(rentDTO.getRentFrom())
				.rentTo(rentDTO.getRentTo())
				.price(rentDTO.getPrice())
				.build();
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
	
	public static List<RentDTO> convertSetRentsToDTO(Set<Rent> rents) {
		List<RentDTO> rentsDTO = new ArrayList<>();
		for (Rent rent : rents) {
			rentsDTO.add(convertRentToDTO(rent));
		}
		return rentsDTO;
	}
	
}
