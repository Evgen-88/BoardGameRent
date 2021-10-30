package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Query;
import org.hibernate.Session;

import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;

public class HibernatePurchaseRepositoryImpl implements PurchaseRepository {
	private static final String ID_COLUMN = "id";
	private static final String ACCESSORY_ID_COLUMN = "accessoryId";
	private static final String ORDER_ID_COLUMN = "orderId";
	private static final String QUANTITY_COLUMN = "quantity";
	private static final String PRICE_COLUMN = "price";
	
	private static final String SELECT_ALL_QUERY = "from Purchase p";
	private static final String UPDATE_QUERY = "update Purchase set accessoryId = :accessoryId, "
			+ "orderId = :orderId, quantity = :quantity, price = :price where id = :id";

	private final Session session;

	public HibernatePurchaseRepositoryImpl(Session session) {
		this.session = session;
	}
	
	@Override
	public List<Purchase> findAll() throws RepositoryException {
		try {
			return session.createQuery(SELECT_ALL_QUERY, Purchase.class).list();
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
	}

	@Override
	public Purchase findById(Long id) throws RepositoryException {
		try {
			return session.get(Purchase.class, id);
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
	}

	@Override
	public List<Purchase> addAll(List<Purchase> purchases) throws RepositoryException {
		try {
			session.getTransaction().begin();
			for (Purchase purchase : purchases) {
				session.save(purchase);
			}
			session.getTransaction().commit();
			return purchases;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: addAll: " + ex);
		}
	}

	@Override
	public Purchase add(Purchase purchase) throws RepositoryException {
		try {
			session.getTransaction().begin();
			session.save(purchase);
			session.getTransaction().commit();
			return purchase;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: add: " + ex);
		}
	}

	@Override
	public Purchase update(Purchase purchase) throws RepositoryException {
		try {
			session.getTransaction().begin();
			Query query = session.createQuery(UPDATE_QUERY);
			insertPurchase(query, purchase);
			query.executeUpdate();
			session.getTransaction().commit();
			return session.get(Purchase.class, purchase.getId());
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: update: " + ex);
		}
	}

	@Override
	public boolean delete(Long id) throws RepositoryException {
		try {
			session.getTransaction().begin();
			Purchase purchase = session.find(Purchase.class, id);
			session.get(Order.class,purchase.getOrderId()).getPurchases().remove(purchase);
			session.remove(purchase);
			session.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: delete: " + ex);
		}
	}

	@Override
	public List<Purchase> findPurchasesByOrder(Long orderId) throws RepositoryException {
		try {
			Order order = session.get(Order.class, orderId);
			if(order != null) {
				return new ArrayList<>(order.getPurchases());
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findPurchasesByOrder: " + ex);
		}
	}

	@Override
	public boolean deletePurchaseFromOrder(Long orderId) throws RepositoryException {
		boolean isDeleted = false;
		try {
			session.getTransaction().begin();
			Order order = session.get(Order.class, orderId);
			if(order != null) {
				order.setPurchases(new HashSet<>());
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
	
	private void insertPurchase(Query query, Purchase purchase) {
		query.setParameter(ACCESSORY_ID_COLUMN, purchase.getAccessoryId());
		query.setParameter(ORDER_ID_COLUMN, purchase.getOrderId());
		query.setParameter(QUANTITY_COLUMN, purchase.getQuantity());
		query.setParameter(PRICE_COLUMN, purchase.getPrice());
		query.setParameter(ID_COLUMN, purchase.getId());
	}

}
