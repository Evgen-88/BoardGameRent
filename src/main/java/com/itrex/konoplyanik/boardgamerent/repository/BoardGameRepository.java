package com.itrex.konoplyanik.boardgamerent.repository;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.entity.BoardGame;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;

public interface BoardGameRepository {
	
	List<BoardGame> findAll() throws RepositoryException;
	BoardGame findById(Long id) throws RepositoryException;
	List<BoardGame> addAll(List<BoardGame> boardGames) throws RepositoryException;
	BoardGame add(BoardGame boardGame) throws RepositoryException;
	BoardGame update(BoardGame boardGame) throws RepositoryException;
	boolean delete(Long id) throws RepositoryException;
	
}
