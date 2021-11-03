package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.itrex.konoplyanik.boardgamerent.entity.Accessory;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.AccessoryRepository;

@Repository
public class AccessoryRepositoryImpl implements AccessoryRepository {
	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "name";
	private static final String PRICE_COLUMN = "price";
	private static final String QUANTITY_COLUMN = "quantity";

	private static final String SELECT_ALL_QUERY = "from Accessory a";
	private static final String UPDATE_QUERY = "update Accessory set name = :name, "
			+ "price = :price, quantity = :quantity where id = :id";

	private Session session;

	public AccessoryRepositoryImpl(SessionFactory sessionFactory) {
		this.session = sessionFactory.openSession();
	}

	@Override
	public List<Accessory> findAll() throws RepositoryException {
		try {
			return session.createQuery(SELECT_ALL_QUERY, Accessory.class).list();
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findAll: " + ex);
		}
	}

	@Override
	public Accessory findById(Long id) throws RepositoryException {
		try {
			return session.get(Accessory.class, id);
		} catch (Exception ex) {
			throw new RepositoryException("EXCEPTION: findById: " + ex);
		}
	}

	@Override
	public List<Accessory> addAll(List<Accessory> accessories) throws RepositoryException {
		try {
			session.getTransaction().begin();
			for (Accessory accessory : accessories) {
				session.save(accessory);
			}
			session.getTransaction().commit();
			return accessories;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: addAll: " + ex);
		}
	}

	@Override
	public Accessory add(Accessory accessory) throws RepositoryException {
		try {
			session.getTransaction().begin();
			session.save(accessory);
			session.getTransaction().commit();
			return accessory;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: add: " + ex);
		}
	}

	@Override
	public Accessory update(Accessory accessory) throws RepositoryException {
		try {
			session.getTransaction().begin();
			Query query = session.createQuery(UPDATE_QUERY);
			insertAccessory(query, accessory);
			query.executeUpdate();
			session.getTransaction().commit();
			return session.get(Accessory.class, accessory.getId());
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: update: " + ex);
		}
	}

	@Override
	public boolean delete(Long id) throws RepositoryException {
		try {
			session.getTransaction().begin();
			Accessory accessory = session.find(Accessory.class, id);
			Set<Purchase> purchases = accessory.getPurchases();
			for(Purchase purchase : purchases) {
				session.remove(purchase);
			}
			session.remove(accessory);
			session.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			throw new RepositoryException("EXCEPTION: delete: " + ex);
		}
	}

	private void insertAccessory(Query query, Accessory accessory) {
		query.setParameter(NAME_COLUMN, accessory.getName());
		query.setParameter(PRICE_COLUMN, accessory.getPrice());
		query.setParameter(QUANTITY_COLUMN, accessory.getQuantity());
		query.setParameter(ID_COLUMN, accessory.getId());
	}

}
