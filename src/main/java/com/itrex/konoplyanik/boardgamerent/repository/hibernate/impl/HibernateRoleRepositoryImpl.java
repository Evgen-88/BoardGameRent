package com.itrex.konoplyanik.boardgamerent.repository.hibernate.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.entity.User;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.hibernate.HibernateRoleRepository;

@Deprecated
@Repository
public class HibernateRoleRepositoryImpl implements HibernateRoleRepository {
	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "name";

	private static final String SELECT_ALL_QUERY = "from Role r";
	private static final String UPDATE_QUERY = "update Role set name = :name where id = :id";

	private final SessionFactory sessionFactory;

	public HibernateRoleRepositoryImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Role> findAll() throws RepositoryException {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery(SELECT_ALL_QUERY, Role.class).list();
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
	}

	@Override
	public Role findById(Long id) throws RepositoryException {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Role.class, id);
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
	}

	@Override
	public Role add(Role role) throws RepositoryException {
		try (Session session = sessionFactory.openSession()) {
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
	}

	@Override
	public Role update(Role role) throws RepositoryException {
		try (Session session = sessionFactory.openSession()) {
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
	}

	@Override
	public boolean delete(Long id) throws RepositoryException {
		try (Session session = sessionFactory.openSession()) {
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
	}
	
	private void insertRole(Query query, Role role) {
		query.setParameter(NAME_COLUMN, role.getName());
		query.setParameter(ID_COLUMN, role.getId());
	}
	
	@Override
	public List<Role> findRolesByUser(Long userId) throws RepositoryException {
		try (Session session = sessionFactory.openSession()) {
			try {
				User user = session.find(User.class, userId);
				return new ArrayList<>(user.getRoles());
			} catch (Exception ex) {
				throw new RepositoryException("EXCEPTION: findRolesByUser: " + ex);
			}
		}
	}

}
