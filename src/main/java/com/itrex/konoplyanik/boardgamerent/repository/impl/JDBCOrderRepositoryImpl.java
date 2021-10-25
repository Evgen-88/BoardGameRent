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

import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Status;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;

public class JDBCOrderRepositoryImpl implements OrderRepository {
	private static final String ID_COLUMN = "id";
	private static final String USER_ID_COLUMN = "board_game_id";
	private static final String TOTAL_PRICE_COLUMN = "total_price";
	private static final String ORDER_DATE_COLUMN = "date";
	private static final String STATUS_COLUMN = "status";

	private static final String INSERT_ORDER_QUERY = "INSERT INTO orders(board_game_id, total_price, order_date, status) VALUES (?, ?, ?, ?)";
	private static final String SELECT_ALL_QUERY = "SELECT	* FROM orders";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM orders WHERE id=";
	private static final String UPDATE_ORDER_QUERY = "UPDATE orders SET board_game_id=?, total_price=?, order_date=?, status=? WHERE id=?";
	private static final String DELETE_ORDER_QUERY = "DELETE FROM orders WHERE id=?";
	private static final String SELECT_BY_USER_QUERY = "SELECT * FROM orders WHERE user_id=?";
	private static final String DELETE_ORDER_BY_USER_QUERY = "DELETE FROM orders WHERE user_id=?";
	private static final String DELETE_RENT_BY_ORDER_QUERY = "DELETE FROM rent WHERE order_id=?";
	private static final String DELETE_PURCHASE_BY_ORDER_QUERY = "DELETE FROM purchase WHERE order_id=?";
	private static final String SELECT_ID_BY_USER_QUERY = "SELECT id FROM orders WHERE user_id=";

	private final DataSource dataSource;

	public JDBCOrderRepositoryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Order> findAll() throws RepositoryException {
		List<Order> orders = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
			while (resultSet.next()) {
				Order order = new Order();
				order = getOrder(resultSet, order);
				orders.add(order);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
		return orders;
	}

	@Override
	public Order findById(Long id) throws RepositoryException {
		Order order = new Order();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
			if (resultSet.next()) {
				order = getOrder(resultSet, order);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
		return order;
	}

	@Override
	public List<Order> addAll(List<Order> orders) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			con.setAutoCommit(false);
			try {
				for (Order o : orders) {
					insertOrder(con, o);
				}
				con.commit();
			} catch (SQLException ex) {
				con.rollback();
				throw new RepositoryException("EXCEPTION: insert orders: " + ex);
			} finally {
				con.setAutoCommit(true);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: addAll: " + ex);
		}
		return orders;
	}

	@Override
	public Order add(Order order) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			insertOrder(con, order);
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: add: " + ex);
		}
		return order;
	}

	@Override
	public Order update(Order order) throws RepositoryException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(UPDATE_ORDER_QUERY,
						Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setLong(1, order.getUserId());
			preparedStatement.setInt(2, order.getTotalPrice());
			preparedStatement.setDate(3, Date.valueOf(order.getDate()));
			preparedStatement.setString(4, order.getDate().toString());
			preparedStatement.setLong(5, order.getId());

			if (preparedStatement.executeUpdate() > 0) {
				return order;
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: update: " + ex);
		}
		return null;
	}

	@Override
	public boolean delete(Long id) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			con.setAutoCommit(false);
			try {
				deleteRentFromOrder(con, id);
				deletePurchaseFromOrder(con, id);
				try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_ORDER_QUERY)) {
					preparedStatement.setLong(1, id);
					return preparedStatement.executeUpdate() == 1;
				}
				// con.commit();
			} catch (SQLException ex) {
				con.rollback();
				throw new RepositoryException("Exception: delete: " + ex);
			} finally {
				con.setAutoCommit(true);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("Exception: delete: " + ex);
		}
	}

	private Order getOrder(ResultSet resultSet, Order order) throws SQLException {
		order.setId(resultSet.getLong(ID_COLUMN));
		order.setUserId(resultSet.getLong(USER_ID_COLUMN));
		order.setTotalPrice(resultSet.getInt(TOTAL_PRICE_COLUMN));
		order.setDate(resultSet.getDate(ORDER_DATE_COLUMN).toLocalDate());
		order.setStatus(Status.valueOf(resultSet.getString(STATUS_COLUMN)));
		return order;
	}

	private void insertOrder(Connection con, Order order) throws SQLException {
		try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_ORDER_QUERY,
				Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setLong(1, order.getUserId());
			preparedStatement.setInt(2, order.getTotalPrice());
			preparedStatement.setDate(3, Date.valueOf(order.getDate()));
			preparedStatement.setString(4, order.getDate().toString());

			final int effectiveRow = preparedStatement.executeUpdate();

			if (effectiveRow == 1) {
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						order.setId(generatedKeys.getLong(ID_COLUMN));
					}
				}
			}
		}
	}

	private void deleteRentFromOrder(Connection con, Long orderId) throws RepositoryException {
		try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_RENT_BY_ORDER_QUERY)) {
			preparedStatement.setLong(1, orderId);
			preparedStatement.execute();
		} catch (SQLException ex) {
			throw new RepositoryException("Exception: deleteRents: " + ex);
		}
	}

	private void deletePurchaseFromOrder(Connection con, Long orderId) throws RepositoryException {
		try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_PURCHASE_BY_ORDER_QUERY)) {
			preparedStatement.setLong(1, orderId);
			preparedStatement.execute();
		} catch (SQLException ex) {
			throw new RepositoryException("Exception: deletePurchases: " + ex);
		}
	}

	@Override
	public List<Order> findOrdersByUser(Long userId) throws RepositoryException {
		List<Order> orders = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_BY_USER_QUERY + userId)) {
			while (resultSet.next()) {
				Order order = new Order();
				order = getOrder(resultSet, order);
				orders.add(order);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
		return orders;
	}

	@Override
	public boolean deleteOrdersByUser(Long userId) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			try (Statement stm = con.createStatement();
					ResultSet resultSet = stm.executeQuery(SELECT_ID_BY_USER_QUERY + userId)) {
				while (resultSet.next()) {
					deleteRentFromOrder(con, resultSet.getLong(ID_COLUMN));
					deletePurchaseFromOrder(con, resultSet.getLong(ID_COLUMN));
				}
			}
			try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_ORDER_BY_USER_QUERY)) {
				preparedStatement.setLong(1, userId);
				return preparedStatement.executeUpdate() == 1;
			}
		} catch (SQLException ex) {
			throw new RepositoryException("Exception: delete: " + ex);
		}
	}

}
