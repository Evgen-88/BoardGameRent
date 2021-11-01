package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.Session;

import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.entity.User;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.RoleRepository;

public class HibernateRoleRepositoryImpl implements RoleRepository {
	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "name";

	private static final String SELECT_ALL_QUERY = "from Role r";
	private static final String UPDATE_QUERY = "update Role set name = :name where id = :id";

	private final Session session;

	public HibernateRoleRepositoryImpl(Session session) {
		this.session = session;
	}

	@Override
	public List<Role> findAll() throws RepositoryException {
		try {
			return session.createQuery(SELECT_ALL_QUERY, Role.class).list();
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
	}

	@Override
	public Role findById(Long id) throws RepositoryException {
		try {
			return session.get(Role.class, id);
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
	}

	@Override
	public List<Role> addAll(List<Role> roles) throws RepositoryException {
		try {
			session.getTransaction().begin();
			for (Role role : roles) {
				session.save(role);
			}
			session.getTransaction().commit();
			return roles;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: addAll: " + ex);
		}
	}

	@Override
	public Role add(Role role) throws RepositoryException {
		try {
			session.getTransaction().begin();
			session.save(role);
			session.getTransaction().commit();
			return role;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: add: " + ex);
		}
	}

	@Override
	public Role update(Role role) throws RepositoryException {
		try {
			session.getTransaction().begin();
			Query query = session.createQuery(UPDATE_QUERY);
			insertRole(query, role);
			query.executeUpdate();
			session.getTransaction().commit();
			return session.get(Role.class, role.getId());
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: update: " + ex);
		}
	}

	@Override
	public boolean delete(Long id) throws RepositoryException {
		try {
			session.getTransaction().begin();
			Role role = session.find(Role.class, id);
			for (User user : role.getUsers()) {
				user.getRoles().remove(role);
			}
			session.remove(role);
			session.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: delete: " + ex);
		}
	}

	@Override
	public List<Role> findRolesByUser(Long userId) throws RepositoryException {
		try {
			User user = session.find(User.class, userId);
			return new ArrayList<>(user.getRoles());
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findRolesByUser: " + ex);
		}
	}

	@Override
	public boolean deleteRoleFromUser(Long roleId, Long userId) throws RepositoryException {
		try {
			session.getTransaction().begin();
			User user = session.find(User.class, userId);
			Role role = session.find(Role.class, roleId);
			Set<Role> roles = user.getRoles();
			roles.remove(role);
			user.setRoles(roles);
			session.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: findRolesByUser: " + ex);
		}
	}

	private void insertRole(Query query, Role role) {
		query.setParameter(NAME_COLUMN, role.getName());
		query.setParameter(ID_COLUMN, role.getId());
	}

}
