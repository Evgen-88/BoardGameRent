package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.entity.User;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
	private static final String ID_COLUMN = "id";
	private static final String USER_ID_COLUMN = "userId";
	private static final String TOTAL_PRICE_COLUMN = "totalPrice";
	private static final String DATE_COLUMN = "date";
	private static final String STATUS_COLUMN = "status";

	private static final String SELECT_ALL_QUERY = "from Order o";
	private static final String UPDATE_QUERY = "update Order set userId = :userId, "
			+ "totalPrice = :totalPrice, date = :date, status = :status where id = :id";

	private final Session session;

	public OrderRepositoryImpl(Session session) {
		this.session = session;
	}

	@Override
	public List<Order> findAll() throws RepositoryException {
		try {
			return session.createQuery(SELECT_ALL_QUERY, Order.class).list();
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
	}

	@Override
	public Order findById(Long id) throws RepositoryException {
		try {
			return session.get(Order.class, id);
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
	}

	@Override
	public List<Order> addAll(List<Order> orders) throws RepositoryException {
		try {
			session.getTransaction().begin();
			for (Order order : orders) {
				session.save(order);
			}
			session.getTransaction().commit();
			return orders;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: addAll: " + ex);
		}
	}

	@Override
	public Order add(Order order) throws RepositoryException {
		try {
			session.getTransaction().begin();
			session.save(order);
			session.getTransaction().commit();
			return order;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: add: " + ex);
		}
	}

	@Override
	public Order update(Order order) throws RepositoryException {
		try {
			session.getTransaction().begin();
			Query query = session.createQuery(UPDATE_QUERY);
			insertOrder(query, order);
			query.executeUpdate();
			session.getTransaction().commit();
			return session.get(Order.class, order.getId());
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: update: " + ex);
		}
	}

	@Override
	public boolean delete(Long id) throws RepositoryException {
		try {
			session.getTransaction().begin();
			Order order = session.find(Order.class, id);
			for (Rent rent : order.getRents()) {
				session.remove(rent);
			}
			for (Purchase purchase : order.getPurchases()) {
				session.remove(purchase);
			}
			session.find(User.class, order.getUserId()).getOrders().remove(order);
			session.remove(order);
			session.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: delete: " + ex);
		}
	}

	@Override
	public List<Order> findOrdersByUser(Long userId) throws RepositoryException {
		try {
			User user = session.get(User.class, userId);
			return new ArrayList<>(user.getOrders());
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findPurchasesByOrder: " + ex);
		}
	}

	@Override
	public boolean deleteOrdersByUser(Long userId) throws RepositoryException {
		try {
			session.getTransaction().begin();
			User user = session.get(User.class, userId);
			for (Order order : user.getOrders()) {
				order.setUser(null);
			}
			session.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: deleteOrdersByUser: " + ex);
		}
	}

	private void insertOrder(Query query, Order order) {
		query.setParameter(USER_ID_COLUMN, order.getUserId());
		query.setParameter(TOTAL_PRICE_COLUMN, order.getTotalPrice());
		query.setParameter(DATE_COLUMN, order.getDate());
		query.setParameter(STATUS_COLUMN, order.getStatus());
		query.setParameter(ID_COLUMN, order.getId());
	}

}
