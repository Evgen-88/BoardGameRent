package com.itrex.konoplyanik.boardgamerent.converters;

import com.itrex.konoplyanik.boardgamerent.dto.BoardGameDTO;
import com.itrex.konoplyanik.boardgamerent.entity.BoardGame;

public class BoardGameConverter {

	public static BoardGame convertBoardGameToEntity(BoardGameDTO boardGameDTO) {
		BoardGame boardGame = new BoardGame();
		boardGame.setId(boardGameDTO.getId());
		boardGame.setName(boardGameDTO.getName());
		boardGame.setRentPrice(boardGameDTO.getRentPrice());
		boardGame.setQuantity(boardGameDTO.getQuantity());
		return boardGame;
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
