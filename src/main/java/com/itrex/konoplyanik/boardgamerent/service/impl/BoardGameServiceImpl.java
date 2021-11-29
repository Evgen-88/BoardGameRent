package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itrex.konoplyanik.boardgamerent.converters.BoardGameConverter;
import com.itrex.konoplyanik.boardgamerent.dto.BoardGameDTO;
import com.itrex.konoplyanik.boardgamerent.entity.BoardGame;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.BoardGameRepository;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;
import com.itrex.konoplyanik.boardgamerent.service.BoardGameService;

@Service
public class BoardGameServiceImpl implements BoardGameService {

	private final BoardGameRepository boardGameRepository;
	private final RentRepository rentRepository;

	public BoardGameServiceImpl(BoardGameRepository boardGameRepository, RentRepository rentRepository) {
		this.boardGameRepository = boardGameRepository;
		this.rentRepository = rentRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BoardGameDTO> findAll() {
		return boardGameRepository.findAll().stream().map(BoardGameConverter::convertBoardGameToDTO)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public BoardGameDTO findById(Long id) {
		return boardGameRepository.findById(id).map(BoardGameConverter::convertBoardGameToDTO)
				.orElseThrow(() -> new ServiceException("BoardGame not found"));
	}

	@Override
	@Transactional
	public BoardGameDTO add(BoardGameDTO boardGame) {
		return BoardGameConverter.convertBoardGameToDTO(
				boardGameRepository.save(BoardGameConverter.convertBoardGameToEntity(boardGame)));
	}

	@Override
	@Transactional
	public BoardGameDTO update(BoardGameDTO boardGameDTO) throws ServiceException {
		BoardGame boardGame = boardGameRepository.findById(boardGameDTO.getId())
				.orElseThrow(() -> new ServiceException("BoardGame not found"));
		boardGame.setName(boardGameDTO.getName());
		boardGame.setRentPrice(boardGameDTO.getRentPrice());
		boardGame.setQuantity(boardGameDTO.getQuantity());
		boardGameRepository.flush();
		return BoardGameConverter.convertBoardGameToDTO(boardGameRepository.findById(boardGame.getId()).get());
	}

	@Override
	@Transactional
	public boolean delete(Long id) throws ServiceException {
		BoardGame boardGame = boardGameRepository.findBoardGameById(id)
				.orElseThrow(() -> new ServiceException("BoardGame not found"));
		for (Rent rent : boardGame.getRents()) {
			rentRepository.delete(rent);
		}
		boardGameRepository.delete(boardGame);
		return true;
	}

}
