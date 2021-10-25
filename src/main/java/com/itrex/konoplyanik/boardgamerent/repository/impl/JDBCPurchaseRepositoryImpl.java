package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;

public class JDBCPurchaseRepositoryImpl implements PurchaseRepository {
	private static final String ID_COLUMN = "id";
	private static final String ACCESSORY_ID_COLUMN = "accessory_id";
	private static final String ORDER_ID_COLUMN = "order_id";
	private static final String QUANTITY_COLUMN = "quantity";
	private static final String PRICE_COLUMN = "price";
	
	private static final String INSERT_PURCHASE_QUERY = "INSERT INTO purchase(accessory_id, order_id, quantity, price) VALUES (?, ?, ?, ?)";
	private static final String SELECT_ALL_QUERY = "SELECT	* FROM purchase";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM purchase WHERE id=";
	private static final String UPDATE_PURCHASE_QUERY = "UPDATE purchase SET accessory_id=?, order_id=?, quantity=?, price=? WHERE id=?";
	private static final String DELETE_PURCHASE_QUERY = "DELETE	FROM purchase WHERE id=?";
	private static final String SELECT_BY_ORDER_QUERY = "SELECT * FROM purchase WHERE order_id=";
	private static final String DELETE_PURCHASE_BY_ORDER_QUERY = "DELETE FROM purchase WHERE order_id=?";
	

	private final DataSource dataSource;

	public JDBCPurchaseRepositoryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Purchase> findAll() throws RepositoryException {
		List<Purchase> purchases = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
			while (resultSet.next()) {
				purchases.add(getPurchase(resultSet));
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
		return purchases;
	}

	@Override
	public Purchase findById(Long id) throws RepositoryException {
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
			if (resultSet.next()) {
				return getPurchase(resultSet);
			} else {
				throw new RepositoryException("EXCEPTION: user not found");
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
	}

	@Override
	public List<Purchase> addAll(List<Purchase> purchases) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			con.setAutoCommit(false);
			try {
				for (Purchase p : purchases) {
					insertPurchase(con, p);
				}
				con.commit();
			} catch (SQLException ex) {
				con.rollback();
				throw new RepositoryException("EXCEPTION: insert purchases: " + ex);
			} finally {
				con.setAutoCommit(true);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: addAll: " + ex);
		}
		return purchases;
	}

	@Override
	public Purchase add(Purchase purchase) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			insertPurchase(con, purchase);
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: add: " + ex);
		}
		return purchase;
	}

	@Override
	public Purchase update(Purchase purchase) throws RepositoryException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(UPDATE_PURCHASE_QUERY,
						Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setLong(1, purchase.getAccessoryId());
			preparedStatement.setLong(2, purchase.getOrderId());
			preparedStatement.setInt(3, purchase.getQuantity());
			preparedStatement.setInt(4, purchase.getPrice());
			preparedStatement.setLong(5, purchase.getId());

			if (preparedStatement.executeUpdate() > 0) {
				return purchase;
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: update: " + ex);
		}
		return null;
	}

	@Override
	public boolean delete(Long id) throws RepositoryException {
		try(Connection con = dataSource.getConnection();PreparedStatement preparedStatement = con.prepareStatement(DELETE_PURCHASE_QUERY)) {
			preparedStatement.setLong(1, id);
			return preparedStatement.executeUpdate() == 1;
		} catch(SQLException ex) {
			throw new RepositoryException("Exception: delete: " + ex);
		}
	}
	
	private Purchase getPurchase(ResultSet resultSet) throws SQLException {
		Purchase purchase = new Purchase();
		purchase.setId(resultSet.getLong(ID_COLUMN));
		purchase.setAccessoryId(resultSet.getLong(ACCESSORY_ID_COLUMN));
		purchase.setOrderId(resultSet.getLong(ORDER_ID_COLUMN));
		purchase.setQuantity(resultSet.getInt(QUANTITY_COLUMN));
		purchase.setPrice(resultSet.getInt(PRICE_COLUMN));
		return purchase;
	}

	private void insertPurchase(Connection con, Purchase purchase) throws SQLException {
		try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_PURCHASE_QUERY,
				Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setLong(1, purchase.getAccessoryId());
			preparedStatement.setLong(2, purchase.getOrderId());
			preparedStatement.setInt(3, purchase.getQuantity());
			preparedStatement.setInt(4, purchase.getPrice());

			final int effectiveRow = preparedStatement.executeUpdate();

			if (effectiveRow == 1) {
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						purchase.setId(generatedKeys.getLong(ID_COLUMN));
					}
				}
			}
		}
	}

	@Override
	public List<Purchase> findPurchasesByOrder(Long orderId) throws RepositoryException {
		List<Purchase> purchases = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_BY_ORDER_QUERY + orderId)) {
			while (resultSet.next()) {
				purchases.add(getPurchase(resultSet));
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findPurchasesByOrder: " + ex);
		}
		return purchases;
	}

	@Override
	public boolean deletePurchaseFromOrder(Long orderId) throws RepositoryException {
		try(Connection con = dataSource.getConnection();PreparedStatement preparedStatement = con.prepareStatement(DELETE_PURCHASE_BY_ORDER_QUERY)) {
			preparedStatement.setLong(1, orderId);
			return preparedStatement.executeUpdate() == 1;
		} catch(SQLException ex) {
			throw new RepositoryException("Exception: delete: " + ex);
		}
	}

}
