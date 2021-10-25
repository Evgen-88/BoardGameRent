package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.itrex.konoplyanik.boardgamerent.entity.Accessory;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.AccessoryRepository;

public class JDBCAccessoryRepositoryImpl implements AccessoryRepository {
	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "name";
	private static final String PRICE_COLUMN = "price";
	private static final String QUANTITY_COLUMN = "quantity";

	private static final String INSERT_ACCESSORY_QUERY = "INSERT INTO accessory(name, price, quantity) VALUES (?, ?, ?)";
	private static final String SELECT_ALL_QUERY = "SELECT	* FROM accessory";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM accessory WHERE id=";
	private static final String UPDATE_ACCESSORY_QUERY = "UPDATE accessory SET name=?, price=?, quantity=? WHERE id=?";
	private static final String DELETE_ACCESSORY_QUERY = "DELETE FROM accessory WHERE id=?";

	private final DataSource dataSource;

	public JDBCAccessoryRepositoryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Accessory> findAll() throws RepositoryException {
		List<Accessory> accessories = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
			while (resultSet.next()) {
				accessories.add(getAccessory(resultSet));
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
		return accessories;
	}

	@Override
	public Accessory findById(Long id) throws RepositoryException {
		Accessory accessory = new Accessory();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
			if (resultSet.next()) {
				accessory = getAccessory(resultSet);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
		return accessory;
	}

	@Override
	public List<Accessory> addAll(List<Accessory> accessories) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			con.setAutoCommit(false);
			try {
				for (Accessory acc : accessories) {
					insertAccessory(con, acc);
				}
				con.commit();
			} catch (SQLException ex) {
				con.rollback();
				throw new RepositoryException("EXCEPTION: insert accessories: " + ex);
			} finally {
				con.setAutoCommit(true);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: addAll: " + ex);
		}
		return accessories;
	}

	@Override
	public Accessory add(Accessory accessory) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			insertAccessory(con, accessory);
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: add: " + ex);
		}
		return accessory;
	}

	@Override
	public Accessory update(Accessory accessory) throws RepositoryException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(UPDATE_ACCESSORY_QUERY,
						Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, accessory.getName());
			preparedStatement.setInt(2, accessory.getPrice());
			preparedStatement.setInt(3, accessory.getQuantity());
			preparedStatement.setLong(4, accessory.getId());

			if (preparedStatement.executeUpdate() > 0) {
				return accessory;
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: update: " + ex);
		}
		return null;
	}

	@Override
	public boolean delete(Long id) throws RepositoryException {
		try(Connection con = dataSource.getConnection();PreparedStatement preparedStatement = con.prepareStatement(DELETE_ACCESSORY_QUERY)) {
			preparedStatement.setLong(1, id);
			return preparedStatement.executeUpdate() == 1;
		} catch(SQLException ex) {
			throw new RepositoryException("Exception: delete: " + ex);
		}
	}

	private Accessory getAccessory(ResultSet resultSet) throws SQLException {
		Accessory accessory = new Accessory();
		accessory.setId(resultSet.getLong(ID_COLUMN));
		accessory.setName(resultSet.getString(NAME_COLUMN));
		accessory.setPrice(resultSet.getInt(PRICE_COLUMN));
		accessory.setQuantity(resultSet.getInt(QUANTITY_COLUMN));
		return accessory;
	}

	private void insertAccessory(Connection con, Accessory accessory) throws SQLException {
		try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_ACCESSORY_QUERY,
				Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, accessory.getName());
			preparedStatement.setInt(2, accessory.getPrice());
			preparedStatement.setInt(3, accessory.getQuantity());

			final int effectiveRow = preparedStatement.executeUpdate();

			if (effectiveRow == 1) {
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						accessory.setId(generatedKeys.getLong(ID_COLUMN));
					}
				}
			}
		}
	}

}
