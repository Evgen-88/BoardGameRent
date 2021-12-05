package com.itrex.konoplyanik.boardgamerent.repository.hibernate;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.entity.BoardGame;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;

@Deprecated
public interface HibernateBoardGameRepository {
	
	List<BoardGame> findAll() throws RepositoryException;
	BoardGame findById(Long id) throws RepositoryException;
	BoardGame add(BoardGame boardGame) throws RepositoryException;
	BoardGame update(BoardGame boardGame) throws RepositoryException;
	boolean delete(Long id) throws RepositoryException;
	
}
