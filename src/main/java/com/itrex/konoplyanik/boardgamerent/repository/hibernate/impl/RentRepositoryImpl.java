package com.itrex.konoplyanik.boardgamerent.repository.hibernate.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.hibernate.RentRepository;

@Deprecated
@Repository
public class RentRepositoryImpl implements RentRepository {
	private static final String ID_COLUMN = "id";
	private static final String RENT_FROM_COLUMN = "rentFrom";
	private static final String RENT_TO_COLUMN = "rentTo";
	private static final String PRICE_COLUMN = "price";

	private static final String SELECT_ALL_QUERY = "from Rent r";
	private static final String UPDATE_QUERY = "update Rent set rentFrom = :rentFrom, rentTo = :rentTo, price = :price where id = :id";

	private final SessionFactory sessionFactory;

	public RentRepositoryImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Rent> findAll() throws RepositoryException {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery(SELECT_ALL_QUERY, Rent.class).list();
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
	}

	@Override
	public Rent findById(Long id) throws RepositoryException {
		try (Session session = sessionFactory.openSession()) {
			return session.find(Rent.class, id);
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
	}

	@Override
	public Rent add(Rent rent) throws RepositoryException {
		try (Session session = sessionFactory.openSession()) {
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
	}

	@Override
	public Rent update(Rent rent) throws RepositoryException {
		try (Session session = sessionFactory.openSession()) {
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
	}

	@Override
	public boolean delete(Long id) throws RepositoryException {
		try (Session session = sessionFactory.openSession()) {
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
	}

	@Override
	public List<Rent> findRentsByOrder(Long orderId) throws RepositoryException {
		try (Session session = sessionFactory.openSession()) {
			try {
				Order order = session.get(Order.class, orderId);
				return new ArrayList<>(order.getRents());
			} catch (Exception ex) {
				throw new RepositoryException("EXCEPTION: findRentsByOrder: " + ex);
			}
		}
	}

	@Override
	public boolean deleteRentsFromOrder(Long orderId) throws RepositoryException {
		try (Session session = sessionFactory.openSession()) {
			try {
				session.getTransaction().begin();
				Order order = session.get(Order.class, orderId);
				for (Rent rent : order.getRents()) {
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
	}

	private void insertRent(Query query, Rent rent) {
		query.setParameter(RENT_FROM_COLUMN, rent.getRentFrom());
		query.setParameter(RENT_TO_COLUMN, rent.getRentTo());
		query.setParameter(PRICE_COLUMN, rent.getPrice());
		query.setParameter(ID_COLUMN, rent.getId());
	}

}
