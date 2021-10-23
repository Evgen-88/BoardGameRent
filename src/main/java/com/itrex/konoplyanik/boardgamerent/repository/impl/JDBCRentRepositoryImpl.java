package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;

public class JDBCRentRepositoryImpl implements RentRepository {
	private static final String ID_COLUMN = "id";
	private static final String BOARD_GAME_ID_COLUMN = "board_game_id";
	private static final String ORDER_ID_COLUMN = "order_id";
	private static final String FROM_COLUMN = "from";
	private static final String TO_COLUMN = "to";
	private static final String PRICE_COLUMN = "price";
	
	private static final String INSERT_RENT_QUERY = "INSERT INTO rent(board_game_id, order_id, from, to, price) VALUES (?, ?, ?, ?, ?)";
	private static final String SELECT_ALL_QUERY = "SELECT	* FROM rent";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM rent WHERE id=?";
	private static final String UPDATE_RENT_QUERY = "UPDATE rent SET board_game_id=?, order_id=?, from=?, to=?, price=? WHERE id=?";
	private static final String DELETE_RENT_QUERY = "DELETE	FROM rent WHERE id=?";
	private static final String SELECT_BY_ORDER_QUERY = "SELECT * FROM rent WHERE order_id=?";
	private static final String DELETE_RENT_BY_ORDER_QUERY = "DELETE FROM rent WHERE order_id=?";
	

	private final DataSource dataSource;

	public JDBCRentRepositoryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public List<Rent> findAll() throws RepositoryException {
		List<Rent> rents = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
			while (resultSet.next()) {
				Rent rent = new Rent();
				rent = getRent(resultSet, rent);
				rents.add(rent);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
		return rents;
	}

	@Override
	public Rent findById(Long id) throws RepositoryException {
		Rent rent = new Rent();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
			if (resultSet.next()) {
				rent = getRent(resultSet, rent);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
		return rent;
	}

	@Override
	public List<Rent> addAll(List<Rent> rents) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			con.setAutoCommit(false);
			try {
				for (Rent r : rents) {
					insertRent(con, r);
				}
				con.commit();
			} catch (SQLException ex) {
				con.rollback();
				throw new RepositoryException("EXCEPTION: insert rents: " + ex);
			} finally {
				con.setAutoCommit(true);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: addAll: " + ex);
		}
		return rents;
	}

	@Override
	public Rent add(Rent rent) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			insertRent(con, rent);
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: add: " + ex);
		}
		return rent;
	}

	@Override
	public Rent update(Rent rent) throws RepositoryException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(UPDATE_RENT_QUERY,
						Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setLong(1, rent.getBoardGameId());
			preparedStatement.setLong(2, rent.getOrderId());
			preparedStatement.setDate(3, Date.valueOf(rent.getFrom()));
			preparedStatement.setDate(4, Date.valueOf(rent.getTo()));
			preparedStatement.setInt(5, rent.getPrice());
			preparedStatement.setLong(6, rent.getId());

			if (preparedStatement.executeUpdate() > 0) {
				return rent;
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: update: " + ex);
		}
		return null;
	}

	@Override
	public boolean delete(Long id) throws RepositoryException {
		try(Connection con = dataSource.getConnection();PreparedStatement preparedStatement = con.prepareStatement(DELETE_RENT_QUERY)) {
			preparedStatement.setLong(1, id);
			return preparedStatement.executeUpdate() == 1;
		} catch(SQLException ex) {
			throw new RepositoryException("Exception: delete: " + ex);
		}
	}
	
	private Rent getRent(ResultSet resultSet, Rent rent) throws SQLException {
		rent.setId(resultSet.getLong(ID_COLUMN));
		rent.setBoardGameId(resultSet.getLong(BOARD_GAME_ID_COLUMN));
		rent.setOrderId(resultSet.getLong(ORDER_ID_COLUMN));
		rent.setFrom(resultSet.getDate(FROM_COLUMN).toLocalDate());
		rent.setTo(resultSet.getDate(TO_COLUMN).toLocalDate());
		rent.setPrice(resultSet.getInt(PRICE_COLUMN));
		return rent;
	}

	private void insertRent(Connection con, Rent rent) throws SQLException {
		try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_RENT_QUERY,
				Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setLong(1, rent.getBoardGameId());
			preparedStatement.setLong(2, rent.getOrderId());
			preparedStatement.setDate(3, Date.valueOf(rent.getFrom()));
			preparedStatement.setDate(4, Date.valueOf(rent.getTo()));
			preparedStatement.setInt(5, rent.getPrice());

			final int effectiveRow = preparedStatement.executeUpdate();

			if (effectiveRow == 1) {
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						rent.setId(generatedKeys.getLong(ID_COLUMN));
					}
				}
			}
		}
	}

	@Override
	public List<Rent> findRentsByOrder(Long orderId) throws RepositoryException {
		List<Rent> rents = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_BY_ORDER_QUERY + orderId)) {
			while (resultSet.next()) {
				Rent rent = new Rent();
				rent = getRent(resultSet, rent);
				rents.add(rent);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findRentsByOrder: " + ex);
		}
		return rents;
	}

	@Override
	public boolean deleteRentFromOrder(Long orderId) throws RepositoryException {
		try(Connection con = dataSource.getConnection();PreparedStatement preparedStatement = con.prepareStatement(DELETE_RENT_BY_ORDER_QUERY)) {
			preparedStatement.setLong(1, orderId);
			return preparedStatement.executeUpdate() == 1;
		} catch(SQLException ex) {
			throw new RepositoryException("Exception: delete: " + ex);
		}
	}

}
