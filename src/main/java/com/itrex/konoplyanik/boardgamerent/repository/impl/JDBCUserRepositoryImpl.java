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

	private static final String INSERT_USER_QUERY = "INSERT INTO users(login, password, name, phone, email) VALUES (?, ?, ?, ?, ?)";
	private static final String SELECT_ALL_QUERY = "SELECT	* FROM users";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM users WHERE id=?";
	private static final String UPDATE_USER_QUERY = "UPDATE users SET login=?, password=?, name=?, phone=?, email=? WHERE id=?";
	private static final String DELETE_USER_QUERY = "DELETE	FROM users WHERE id=?";
	private static final String SELECT_USERS_BY_ROLE_QUERY = "SELECT users_user_role_link.user_id, users.login, users.password, users.name, users.phone, users.email FROM users_user_role_link LEFT JOIN users ON users_user_role_link.role_id=users.id WHERE users_user_role_link.role_id=?";
	
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
				User user = new User();
				user = getUser(resultSet, user);
				users.add(user);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
		return users;
	}

	@Override
	public User findById(Long id) throws RepositoryException {
		User user = new User();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
			if (resultSet.next()) {
				user = getUser(resultSet, user);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
		return user;
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
		try(Connection con = dataSource.getConnection();PreparedStatement preparedStatement = con.prepareStatement(DELETE_USER_QUERY)) {
			preparedStatement.setLong(1, id);
			return preparedStatement.executeUpdate() == 1;
		} catch(SQLException ex) {
			throw new RepositoryException("Exception: delete: " + ex);
		}
	}

	private User getUser(ResultSet resultSet, User user) throws SQLException {
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
	
	@Override
	public List<User> findUsersByRole(Long id) throws RepositoryException {
		List<User> users = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_USERS_BY_ROLE_QUERY)) {
			while (resultSet.next()) {
				User user = new User();
				user = getUser(resultSet, user);
				users.add(user);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
		return users;
	}

}
