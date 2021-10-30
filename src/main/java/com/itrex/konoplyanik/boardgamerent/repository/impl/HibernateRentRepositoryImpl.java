package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;

public class HibernateRentRepositoryImpl implements RentRepository {
	private static final String ID_COLUMN = "id";
	private static final String BOARD_GAME_ID_COLUMN = "boardGameId";
	private static final String ORDER_ID_COLUMN = "orderId";
	private static final String RENT_FROM_COLUMN = "rentFrom";
	private static final String RENT_TO_COLUMN = "rentTo";
	private static final String PRICE_COLUMN = "price";
	
	private static final String SELECT_ALL_QUERY = "from Rent r";
	private static final String UPDATE_QUERY = "update Rent set boardGameId = :boardGameId, "
			+ "orderId = :orderId, rentFrom = :rentFrom, rentTo = :rentTo, price = :price where id = :id";
	
	private final Session session;

	public HibernateRentRepositoryImpl(Session session) {
		this.session = session;
	}

	@Override
	public List<Rent> findAll() throws RepositoryException {
		try {
			return session.createQuery(SELECT_ALL_QUERY, Rent.class).list();
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
	}

	@Override
	public Rent findById(Long id) throws RepositoryException {
		try {
			return session.get(Rent.class, id);
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
	}

	@Override
	public List<Rent> addAll(List<Rent> rents) throws RepositoryException {
		try {
			session.getTransaction().begin();
			for (Rent rent : rents) {
				session.save(rent);
			}
			session.getTransaction().commit();
			return rents;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: addAll: " + ex);
		}
	}

	@Override
	public Rent add(Rent rent) throws RepositoryException {
		try {
			session.getTransaction().begin();
			session.save(rent);
			session.getTransaction().commit();
			return rent;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: add: " + ex);
		}
	}

	@Override
	public Rent update(Rent rent) throws RepositoryException {
		try {
			session.getTransaction().begin();
			Query query = session.createQuery(UPDATE_QUERY);
			insertRent(query, rent);
			query.executeUpdate();
			session.getTransaction().commit();
			return session.get(Rent.class, rent.getId());
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: update: " + ex);
		}
	}

	@Override
	public boolean delete(Long id) throws RepositoryException {
		try {
			session.getTransaction().begin();
			Rent rent = session.find(Rent.class, id);
			session.get(Order.class,rent.getOrderId()).getRents().remove(rent);
			session.remove(rent);
			session.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: delete: " + ex);
		}
	}

	@Override
	public List<Rent> findRentsByOrder(Long orderId) throws RepositoryException {
		try {
			Order order = session.get(Order.class, orderId);
			if(order != null) {
				return new ArrayList<>(order.getRents());
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findPurchasesByOrder: " + ex);
		}
	}

	@Override
	public boolean deleteRentFromOrder(Long orderId) throws RepositoryException {
		boolean isDeleted = false;
		try {
			session.getTransaction().begin();
			Order order = session.get(Order.class, orderId);
			if(order != null) {
				order.setRents(new HashSet<>());
				isDeleted = true;
			} else {
				isDeleted = false;
			}
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
		return isDeleted;
	}
	
	private void insertRent(Query query, Rent rent) {
		query.setParameter(BOARD_GAME_ID_COLUMN, rent.getBoardGameId());
		query.setParameter(ORDER_ID_COLUMN, rent.getOrderId());
		query.setParameter(RENT_FROM_COLUMN, rent.getRentFrom());
		query.setParameter(RENT_TO_COLUMN, rent.getRentTo());
		query.setParameter(PRICE_COLUMN, rent.getPrice());
		query.setParameter(ID_COLUMN, rent.getId());
	}

}
