package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;

@Repository
public class RentRepositoryImpl implements RentRepository {
	private static final String ID_COLUMN = "id";
	private static final String RENT_FROM_COLUMN = "rentFrom";
	private static final String RENT_TO_COLUMN = "rentTo";
	private static final String PRICE_COLUMN = "price";
	
	private static final String SELECT_ALL_QUERY = "from Rent r";
	private static final String UPDATE_QUERY = "update Rent set rentFrom = :rentFrom, rentTo = :rentTo, price = :price where id = :id";
	
	private final Session session;

	public RentRepositoryImpl(Session session) {
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
			return new ArrayList<>(order.getRents());
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findRentsByOrder: " + ex);
		}
	}

	@Override
	public boolean deleteRentsFromOrder(Long orderId) throws RepositoryException {
		try {
			session.getTransaction().begin();
			Order order = session.get(Order.class, orderId);
			for(Rent rent : order.getRents()) {
				session.remove(rent);
			}
			order.setRents(new HashSet<>());
			session.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
	}
	
	private void insertRent(Query query, Rent rent) {
		query.setParameter(RENT_FROM_COLUMN, rent.getRentFrom());
		query.setParameter(RENT_TO_COLUMN, rent.getRentTo());
		query.setParameter(PRICE_COLUMN, rent.getPrice());
		query.setParameter(ID_COLUMN, rent.getId());
	}

}