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
	
	private static final String INSERT_ROLE_QUERY = "INSERT INTO user_role(name) VALUES (?)";
	private static final String SELECT_ALL_QUERY = "SELECT	* FROM user_role";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM user_role WHERE id=?";
	private static final String UPDATE_ROLE_QUERY = "UPDATE user_role SET name=? WHERE id=?";
	private static final String DELETE_ROLE_QUERY = "DELETE	FROM user_role WHERE id=?";
	private static final String SELECT_ROLES_BY_USER_QUERY = "SELECT users_user_role_link.role_id, user_role.name FROM users_user_role_link LEFT JOIN user_role ON users_user_role_link.role_id=user_role.id WHERE users_user_role_link.user_id=?";
	private static final String DELETE_ROLE_FROM_USER_QUERY = "DELETE FROM users_user_role_link WHERE role_id=? AND user_id=?";
	
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
				Role role = new Role();
				role = getRole(resultSet, role);
				roles.add(role);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
		return roles;
	}

	@Override
	public Role findById(Long id) throws RepositoryException {
		Role role = new Role();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
			if (resultSet.next()) {
				role = getRole(resultSet, role);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
		return role;
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
		try(Connection con = dataSource.getConnection();PreparedStatement preparedStatement = con.prepareStatement(DELETE_ROLE_QUERY)) {
			preparedStatement.setLong(1, id);
			return preparedStatement.executeUpdate() == 1;
		} catch(SQLException ex) {
			throw new RepositoryException("Exception: delete: " + ex);
		}
	}
	
	private Role getRole(ResultSet resultSet, Role role) throws SQLException {
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


	@Override
	public List<Role> findRolesByUser(Long userId) throws RepositoryException {
		List<Role> roles = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement stm = con.createStatement();
				ResultSet resultSet = stm.executeQuery(SELECT_ROLES_BY_USER_QUERY)) {
			while (resultSet.next()) {
				Role role = new Role();
				role = getRole(resultSet, role);
				roles.add(role);
			}
		} catch (SQLException ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
		return roles;
	}
	
	@Override
	public boolean deleteRoleFromUser(Long roleId, Long userId) throws RepositoryException {
		try(Connection con = dataSource.getConnection();PreparedStatement preparedStatement = con.prepareStatement(DELETE_ROLE_FROM_USER_QUERY)) {
			preparedStatement.setLong(1, roleId);
			preparedStatement.setLong(2, userId);
			return preparedStatement.executeUpdate() > 0;
		} catch(SQLException ex) {
			throw new RepositoryException("Exception: delete: " + ex);
		}
	}

}
