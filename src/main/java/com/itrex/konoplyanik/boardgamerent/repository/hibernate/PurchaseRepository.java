package com.itrex.konoplyanik.boardgamerent.repository.hibernate;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;

@Deprecated
public interface PurchaseRepository {

	List<Purchase> findAll() throws RepositoryException;
	Purchase findById(Long id) throws RepositoryException;
	Purchase add(Purchase purchase) throws RepositoryException;
	Purchase update(Purchase purchase) throws RepositoryException;
	boolean delete(Long id) throws RepositoryException;
	
	List<Purchase> findPurchasesByOrder(Long orderId) throws RepositoryException;
	boolean deletePurchasesFromOrder(Long orderId) throws RepositoryException;
}
