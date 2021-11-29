package com.itrex.konoplyanik.boardgamerent.service;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.dto.BoardGameDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

public interface BoardGameService {

	List<BoardGameDTO> findAll();
	BoardGameDTO findById(Long id) throws ServiceException;
	BoardGameDTO add(BoardGameDTO boardGame);
	BoardGameDTO update(BoardGameDTO boardGame) throws ServiceException;
	boolean delete(Long id) throws ServiceException;
}
