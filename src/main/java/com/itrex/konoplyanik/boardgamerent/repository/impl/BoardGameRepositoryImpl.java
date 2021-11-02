package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.itrex.konoplyanik.boardgamerent.entity.BoardGame;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.BoardGameRepository;

@Repository
public class BoardGameRepositoryImpl implements BoardGameRepository {
	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "name";
	private static final String RENT_PRICE_COLUMN = "rentPrice";
	private static final String QUANTITY_COLUMN = "quantity";

	private static final String SELECT_ALL_QUERY = "from BoardGame a";
	private static final String UPDATE_QUERY = "update BoardGame set name = :name, "
			+ "rentPrice = :rentPrice, quantity = :quantity where id = :id";

	private final Session session;

	public BoardGameRepositoryImpl(Session session) {
		this.session = session;
	}

	@Override
	public List<BoardGame> findAll() throws RepositoryException {
		try {
			return session.createQuery(SELECT_ALL_QUERY, BoardGame.class).list();
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
	}

	@Override
	public BoardGame findById(Long id) throws RepositoryException {
		try {
			return session.get(BoardGame.class, id);
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
	}

	@Override
	public List<BoardGame> addAll(List<BoardGame> boardGames) throws RepositoryException {
		try {
			session.getTransaction().begin();
			for (BoardGame boardGame : boardGames) {
				session.save(boardGame);
			}
			session.getTransaction().commit();
			return boardGames;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: addAll: " + ex);
		}
	}

	@Override
	public BoardGame add(BoardGame boardGame) throws RepositoryException {
		try {
			session.getTransaction().begin();
			session.save(boardGame);
			session.getTransaction().commit();
			return boardGame;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: add: " + ex);
		}
	}

	@Override
	public BoardGame update(BoardGame boardGame) throws RepositoryException {
		try {
			session.getTransaction().begin();
			Query query = session.createQuery(UPDATE_QUERY);
			insertBoardGame(query, boardGame);
			query.executeUpdate();
			session.getTransaction().commit();
			return session.get(BoardGame.class, boardGame.getId());
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: update: " + ex);
		}
	}

	@Override
	public boolean delete(Long id) throws RepositoryException {
		try {
			session.getTransaction().begin();
			BoardGame boardGame = session.find(BoardGame.class, id);
			Set<Rent> rents = boardGame.getRents();
			for(Rent rent : rents) {
				session.remove(rent);
			}
			session.remove(boardGame);
			session.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: delete: " + ex);
		}
	}

	private void insertBoardGame(Query query, BoardGame boardGame) {
		query.setParameter(NAME_COLUMN, boardGame.getName());
		query.setParameter(RENT_PRICE_COLUMN, boardGame.getRentPrice());
		query.setParameter(QUANTITY_COLUMN, boardGame.getQuantity());
		query.setParameter(ID_COLUMN, boardGame.getId());
	}

}
