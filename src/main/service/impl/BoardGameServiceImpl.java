package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itrex.konoplyanik.boardgamerent.converters.BoardGameConverter;
import com.itrex.konoplyanik.boardgamerent.dto.BoardGameDTO;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.BoardGameRepository;
import com.itrex.konoplyanik.boardgamerent.service.BoardGameService;

@Service
public class BoardGameServiceImpl implements BoardGameService {

	private final BoardGameRepository boardGameRepository;
	
	public BoardGameServiceImpl(BoardGameRepository boardGameRepository) {
		this.boardGameRepository = boardGameRepository;
	}

	@Override
	public List<BoardGameDTO> findAll() throws ServiceException {
		try {
			return boardGameRepository.findAll().stream()
					.map(BoardGameConverter::convertBoardGameToDTO)
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findAll: " + ex);
		}
	}

	@Override
	public BoardGameDTO findById(Long id) throws ServiceException {
		try {
			return BoardGameConverter.convertBoardGameToDTO(boardGameRepository.findById(id));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findById: " + ex);
		}
	}

	@Override
	public BoardGameDTO add(BoardGameDTO boardGame) throws ServiceException {
		try {
			return BoardGameConverter.convertBoardGameToDTO(boardGameRepository.add(BoardGameConverter.convertBoardGameToEntity(boardGame)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: add: " + ex);
		}
	}

	@Override
	public BoardGameDTO update(BoardGameDTO boardGame) throws ServiceException {
		try {
			return BoardGameConverter.convertBoardGameToDTO(boardGameRepository.update(BoardGameConverter.convertBoardGameToEntity(boardGame)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: update: " + ex);
		}
	}

	@Override
	public boolean delete(Long id) throws ServiceException {
		try {
			return boardGameRepository.delete(id);
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: update: " + ex);
		}
	}

}