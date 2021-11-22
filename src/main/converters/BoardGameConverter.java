package com.itrex.konoplyanik.boardgamerent.converters;

import com.itrex.konoplyanik.boardgamerent.dto.BoardGameDTO;
import com.itrex.konoplyanik.boardgamerent.entity.BoardGame;

public class BoardGameConverter {

	public static BoardGame convertBoardGameToEntity(BoardGameDTO boardGameDTO) {
		return BoardGame.builder()
		.id(boardGameDTO.getId())
		.name(boardGameDTO.getName())
		.rentPrice(boardGameDTO.getRentPrice())
		.quantity(boardGameDTO.getQuantity())
		.build();
	}

	public static BoardGameDTO convertBoardGameToDTO(BoardGame boardGame) {
		return BoardGameDTO.builder()
				.id(boardGame.getId())
				.name(boardGame.getName())
				.rentPrice(boardGame.getRentPrice())
				.quantity(boardGame.getQuantity())
				.build();
	}
}
