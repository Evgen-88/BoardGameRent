package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.itrex.konoplyanik.boardgamerent.entity.BoardGame;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.BoardGameRepository;

public class JDBCBoardGameRepositoryImpl implements BoardGameRepository {
	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "name";
	private static final String RENT_PRICE_COLUMN = "rent_price";
	private static final String QUANTITY_COLUMN = "quantity";

	private static final String INSERT_BOARD_GAME_QUERY = "INSERT INTO board_game(name, rent_price, quantity) VALUES (?, ?, ?)";
	private static final String SELECT_ALL_QUERY = "SELECT	* FROM board_game";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM board_game WHERE id=";
	private static final String UPDATE_BOARD_GAME_QUERY = "UPDATE board_game SET name=?, rent_price=?, quantity=? WHERE id=?";
	private static final String DELETE_BOARD_GAME_QUERY = "DELETE FROM board_game WHERE id=?";
	private static final String DELETE_BOARD_GAME_FROM_RENT_QUERY = "DELETE FROM rent WHERE board_game_id=?";

	private final DataSource dataSource;

	public JDBCBoardGameRepositoryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<BoardGame> findAll() throws RepositoryException {
		List<BoardGame> boardGames = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
			while (resultSet.next()) {
				boardGames.add(getBoardGame(resultSet));
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
		return boardGames;
	}

	@Override
	public BoardGame findById(Long id) throws RepositoryException {
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
			if (resultSet.next()) {
				return getBoardGame(resultSet);
			} else {
				throw new RepositoryException("EXCEPTION: board game not found");
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
	}

	@Override
	public List<BoardGame> addAll(List<BoardGame> boardGames) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			con.setAutoCommit(false);
			try {
				for (BoardGame bg : boardGames) {
					insertBoardGame(con, bg);
				}
				con.commit();
			} catch (SQLException ex) {
				con.rollback();
				throw new RepositoryException("EXCEPTION: insert boardGames: " + ex);
			} finally {
				con.setAutoCommit(true);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: addAll: " + ex);
		}
		return boardGames;
	}

	@Override
	public BoardGame add(BoardGame boardGame) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			insertBoardGame(con, boardGame);
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: add: " + ex);
		}
		return boardGame;
	}

	@Override
	public BoardGame update(BoardGame boardGame) throws RepositoryException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(UPDATE_BOARD_GAME_QUERY,
						Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, boardGame.getName());
			preparedStatement.setInt(2, boardGame.getRentPrice());
			preparedStatement.setInt(3, boardGame.getQuantity());
			preparedStatement.setLong(4, boardGame.getId());

			if (preparedStatement.executeUpdate() > 0) {
				return boardGame;
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: update: " + ex);
		}
		return null;
	}

	@Override
	public boolean delete(Long id) throws RepositoryException {
		boolean isDeleted;
		try (Connection con = dataSource.getConnection()) {
			con.setAutoCommit(false);
			try {
				deleteBoardGameFromRent(con, id);
				try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_BOARD_GAME_QUERY)) {
					preparedStatement.setLong(1, id);
					isDeleted = preparedStatement.executeUpdate() == 1;
				}
				con.commit();
			} catch (SQLException ex) {
				con.rollback();
				throw new RepositoryException("Exception: delete: " + ex);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("Exception: delete: " + ex);
		}
		return isDeleted;
	}

	private BoardGame getBoardGame(ResultSet resultSet) throws SQLException {
		BoardGame boardGame = new BoardGame();
		boardGame.setId(resultSet.getLong(ID_COLUMN));
		boardGame.setName(resultSet.getString(NAME_COLUMN));
		boardGame.setRentPrice(resultSet.getInt(RENT_PRICE_COLUMN));
		boardGame.setQuantity(resultSet.getInt(QUANTITY_COLUMN));
		return boardGame;
	}

	private void insertBoardGame(Connection con, BoardGame boardGame) throws SQLException {
		try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_BOARD_GAME_QUERY,
				Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, boardGame.getName());
			preparedStatement.setInt(2, boardGame.getRentPrice());
			preparedStatement.setInt(3, boardGame.getQuantity());

			final int effectiveRow = preparedStatement.executeUpdate();

			if (effectiveRow == 1) {
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						boardGame.setId(generatedKeys.getLong(ID_COLUMN));
					}
				}
			}
		}
	}

	private void deleteBoardGameFromRent(Connection con, Long boardGameId) throws RepositoryException {
		try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_BOARD_GAME_FROM_RENT_QUERY)) {
			preparedStatement.setObject(1, boardGameId);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new RepositoryException("Exception: deleteRents: " + ex);
		}
	}

}
