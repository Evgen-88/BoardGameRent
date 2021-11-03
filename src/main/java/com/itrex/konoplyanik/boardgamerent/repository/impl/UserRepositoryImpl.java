package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.entity.User;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
	private static final String ID_COLUMN = "id";
	private static final String LOGIN_COLUMN = "login";
	private static final String PASSWORD_COLUMN = "password";
	private static final String NAME_COLUMN = "name";
	private static final String PHONE_COLUMN = "phone";
	private static final String EMAIL_COLUMN = "email";

	private static final String SELECT_ALL_QUERY = "from User u";
	private static final String UPDATE_QUERY = "update User set login = :login, "
			+ "password = :password, name = :name, phone = :phone, email = :email where id = :id";

	private final Session session;

	public UserRepositoryImpl(SessionFactory sessionFactory) {
		this.session = sessionFactory.openSession();
	}

	@Override
	public List<User> findAll() throws RepositoryException {
		try {
			return session.createQuery(SELECT_ALL_QUERY, User.class).list();
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
	}

	@Override
	public User findById(Long id) throws RepositoryException {
		try {
			return session.get(User.class, id);
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
	}

	@Override
	public List<User> addAll(List<User> users, Long... roleIds) throws RepositoryException {
		try {
			session.getTransaction().begin();
			for (User user : users) {
				Set<Role> roles = new HashSet<>();
				for (Long roleId : roleIds) {
					roles.add(session.find(Role.class, roleId));
				}
				user.setRoles(roles);
				session.save(user);
			}
			session.getTransaction().commit();
			return users;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: addAll: " + ex);
		}
	}

	@Override
	public User add(User user, Long... roleIds) throws RepositoryException {
		try {
			session.getTransaction().begin();
			Set<Role> roles = new HashSet<>();
			for (Long roleId : roleIds) {
				roles.add(session.find(Role.class, roleId));
			}
			user.setRoles(roles);
			session.save(user);
			session.getTransaction().commit();
			return user;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: add: " + ex);
		}
	}

	@Override
	public User update(User user) throws RepositoryException {
		try {
			session.getTransaction().begin();
			Query query = session.createQuery(UPDATE_QUERY);
			insertUser(query, user);
			query.executeUpdate();
			session.getTransaction().commit();
			return session.get(User.class, user.getId());
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: update: " + ex);
		}
	}

	@Override
	public boolean delete(Long id) throws RepositoryException {
		try {
			session.getTransaction().begin();
			User user = session.find(User.class, id);
			for(Order order : user.getOrders()) {
				deleteOrders(order);
			}
			session.remove(user);
			session.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: delete: " + ex);
		}
	}

	@Override
	public List<User> findUsersByRole(Long id) throws RepositoryException {
		try {
			Role role = session.find(Role.class, id);
			return new ArrayList<>(role.getUsers());
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findUsersByRole: " + ex);
		}
	}

	private void insertUser(Query query, User user) {
		query.setParameter(LOGIN_COLUMN, user.getLogin());
		query.setParameter(PASSWORD_COLUMN, user.getPassword());
		query.setParameter(NAME_COLUMN, user.getName());
		query.setParameter(PHONE_COLUMN, user.getPhone());
		query.setParameter(EMAIL_COLUMN, user.getEmail());
		query.setParameter(ID_COLUMN, user.getId());
	}
	
	private void deleteOrders(Order order) {
		for (Rent rent : order.getRents()) {
			session.remove(rent);
		}
		for (Purchase purchase : order.getPurchases()) {
			session.remove(purchase);
		}
		session.remove(order);
	}

}
