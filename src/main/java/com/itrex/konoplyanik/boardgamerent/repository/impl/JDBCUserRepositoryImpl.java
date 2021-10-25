package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.itrex.konoplyanik.boardgamerent.entity.User;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.UserRepository;

public class JDBCUserRepositoryImpl implements UserRepository {
	private static final String ID_COLUMN = "id";
	private static final String LOGIN_COLUMN = "login";
	private static final String PASSWORD_COLUMN = "password";
	private static final String NAME_COLUMN = "name";
	private static final String PHONE_COLUMN = "phone";
	private static final String EMAIL_COLUMN = "email";

	private static final String USER_ID_COLUMN = "user_id";

	private static final String INSERT_USER_QUERY = "INSERT INTO users(login, password, name, phone, email) VALUES (?, ?, ?, ?, ?)";
	private static final String SELECT_ALL_QUERY = "SELECT	* FROM users";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM users WHERE id=";
	private static final String UPDATE_USER_QUERY = "UPDATE users SET login=?, password=?, name=?, phone=?, email=? WHERE id=?";
	private static final String DELETE_USER_QUERY = "DELETE	FROM users WHERE id=?";
	private static final String SELECT_USERS_BY_ROLE_QUERY = "SELECT uurl.user_id, u.login, u.password, u.name, u.phone, u.email FROM users_user_role_link AS uurl LEFT JOIN users AS u ON uurl.user_id=u.id WHERE uurl.role_id=";
	private static final String DELETE_USER_LINK_QUERY = "DELETE FROM users_user_role_link WHERE user_id=?";

	private final DataSource dataSource;

	public JDBCUserRepositoryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<User> findAll() throws RepositoryException {
		List<User> users = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
			while (resultSet.next()) {
				users.add(getUser(resultSet));
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
		return users;
	}

	@Override
	public User findById(Long id) throws RepositoryException {
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
			if (resultSet.next()) {
				return getUser(resultSet);
			} else {
				throw new RepositoryException("EXCEPTION: user not found");
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
	}

	@Override
	public List<User> addAll(List<User> users) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			con.setAutoCommit(false);
			try {
				for (User u : users) {
					insertUser(con, u);
				}
				con.commit();
			} catch (SQLException ex) {
				con.rollback();
				throw new RepositoryException("EXCEPTION: insert users: " + ex);
			} finally {
				con.setAutoCommit(true);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: addAll: " + ex);
		}
		return users;
	}

	@Override
	public User add(User user) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			insertUser(con, user);
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: add: " + ex);
		}
		return user;
	}

	@Override
	public User update(User user) throws RepositoryException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(UPDATE_USER_QUERY,
						Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getName());
			preparedStatement.setInt(4, user.getPhone());
			preparedStatement.setString(5, user.getEmail());
			preparedStatement.setLong(6, user.getId());

			if (preparedStatement.executeUpdate() > 0) {
				return user;
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: update: " + ex);
		}
		return null;
	}

	@Override
	public boolean delete(Long id) throws RepositoryException {
		boolean isDeleted = false;
		try (Connection con = dataSource.getConnection()) {
			con.setAutoCommit(false);
			try {
				deleteRolesFromUser(con, id);
				new JDBCOrderRepositoryImpl(dataSource).deleteOrdersByUser(id);
				try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_USER_QUERY)) {
					preparedStatement.setLong(1, id);
					isDeleted =  preparedStatement.executeUpdate() == 1;
				}
				con.commit();
			} catch (SQLException ex) {
				con.rollback();
				throw new RepositoryException("Exception: delete: " + ex);
			} finally {
				con.setAutoCommit(true);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("Exception: delete: " + ex);
		}
		return isDeleted;
	}

	private User getUser(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong(ID_COLUMN));
		user.setLogin(resultSet.getString(LOGIN_COLUMN));
		user.setPassword(resultSet.getString(PASSWORD_COLUMN));
		user.setName(resultSet.getString(NAME_COLUMN));
		user.setPhone(resultSet.getInt(PHONE_COLUMN));
		user.setEmail(resultSet.getString(EMAIL_COLUMN));
		return user;
	}

	private void insertUser(Connection con, User user) throws SQLException {
		try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_USER_QUERY,
				Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getName());
			preparedStatement.setInt(4, user.getPhone());
			preparedStatement.setString(5, user.getEmail());

			final int effectiveRow = preparedStatement.executeUpdate();

			if (effectiveRow == 1) {
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						user.setId(generatedKeys.getLong(ID_COLUMN));
					}
				}
			}
		}
	}

	private void deleteRolesFromUser(Connection con, Long userId) throws SQLException {
		try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_USER_LINK_QUERY)) {
			preparedStatement.setLong(1, userId);
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public List<User> findUsersByRole(Long roleId) throws RepositoryException {
		List<User> users = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_USERS_BY_ROLE_QUERY + roleId)) {
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getLong(USER_ID_COLUMN));
				user.setLogin(resultSet.getString(LOGIN_COLUMN));
				user.setPassword(resultSet.getString(PASSWORD_COLUMN));
				user.setName(resultSet.getString(NAME_COLUMN));
				user.setPhone(resultSet.getInt(PHONE_COLUMN));
				user.setEmail(resultSet.getString(EMAIL_COLUMN));
				users.add(user);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
		return users;
	}

}
