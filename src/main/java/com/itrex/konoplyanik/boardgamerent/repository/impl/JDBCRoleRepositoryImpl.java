package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.RoleRepository;

public class JDBCRoleRepositoryImpl implements RoleRepository {
	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "name";
	
	private static final String ROLE_ID_COLUMN = "role_id";

	private static final String INSERT_ROLE_QUERY = "INSERT INTO user_role(name) VALUES (?)";
	private static final String SELECT_ALL_QUERY = "SELECT	* FROM user_role";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM user_role WHERE id=";
	private static final String UPDATE_ROLE_QUERY = "UPDATE user_role SET name=? WHERE id=?";
	private static final String DELETE_ROLE_QUERY = "DELETE	FROM user_role WHERE id=?";
	private static final String SELECT_ROLES_BY_USER_QUERY = "SELECT uurl.role_id, ur.name FROM users_user_role_link AS uurl LEFT JOIN user_role AS ur ON uurl.role_id=ur.id WHERE uurl.user_id=";
	private static final String DELETE_ROLE_FROM_USER_QUERY = "DELETE FROM users_user_role_link WHERE role_id=? AND user_id=?";
	private static final String DELETE_ROLE_LINK_QUERY = "DELETE FROM users_user_role_link WHERE role_id=?";

	private final DataSource dataSource;

	public JDBCRoleRepositoryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Role> findAll() throws RepositoryException {
		List<Role> roles = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
			while (resultSet.next()) {
				roles.add(getRole(resultSet));
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
		return roles;
	}

	@Override
	public Role findById(Long id) throws RepositoryException {
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
			if (resultSet.next()) {
				return getRole(resultSet);
			} else {
				throw new RepositoryException("EXCEPTION: role not found");
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
	}

	@Override
	public List<Role> addAll(List<Role> roles) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			con.setAutoCommit(false);
			try {
				for (Role r : roles) {
					insertRole(con, r);
				}
				con.commit();
			} catch (SQLException ex) {
				con.rollback();
				throw new RepositoryException("EXCEPTION: insert roles: " + ex);
			} finally {
				con.setAutoCommit(true);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: addAll: " + ex);
		}
		return roles;
	}

	@Override
	public Role add(Role role) throws RepositoryException {
		try (Connection con = dataSource.getConnection()) {
			insertRole(con, role);
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: add: " + ex);
		}
		return role;
	}

	@Override
	public Role update(Role role) throws RepositoryException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(UPDATE_ROLE_QUERY,
						Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, role.getName());
			preparedStatement.setLong(2, role.getId());

			if (preparedStatement.executeUpdate() > 0) {
				return role;
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
				deleteRoleLink(con, id);
				try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_ROLE_QUERY)) {
					preparedStatement.setLong(1, id);
					isDeleted = preparedStatement.executeUpdate() == 1;
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

	private Role getRole(ResultSet resultSet) throws SQLException {
		Role role = new Role();
		role.setId(resultSet.getLong(ID_COLUMN));
		role.setName(resultSet.getString(NAME_COLUMN));
		return role;
	}

	private void insertRole(Connection con, Role role) throws SQLException {
		try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_ROLE_QUERY,
				Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, role.getName());

			final int effectiveRow = preparedStatement.executeUpdate();

			if (effectiveRow == 1) {
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						role.setId(generatedKeys.getLong(ID_COLUMN));
					}
				}
			}
		}
	}

	private void deleteRoleLink(Connection con, Long roleId) throws SQLException {
		try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_ROLE_LINK_QUERY)) {
			preparedStatement.setLong(1, roleId);
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public List<Role> findRolesByUser(Long userId) throws RepositoryException {
		List<Role> roles = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_ROLES_BY_USER_QUERY + userId)) {
			while (resultSet.next()) {
				Role role = new Role();
				role.setId(resultSet.getLong(ROLE_ID_COLUMN));
				role.setName(resultSet.getString(NAME_COLUMN));
				roles.add(role);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
		return roles;
	}

	@Override
	public boolean deleteRoleFromUser(Long roleId, Long userId) throws RepositoryException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(DELETE_ROLE_FROM_USER_QUERY)) {
			preparedStatement.setLong(1, roleId);
			preparedStatement.setLong(2, userId);
			return preparedStatement.executeUpdate() == 1;
		} catch (SQLException ex) {
			throw new RepositoryException("Exception: deleteRoleFromUser: " + ex);
		}
	}

}
