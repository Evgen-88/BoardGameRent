package com.itrex.konoplyanik.boardgamerent.repository;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;

public interface PurchaseRepository {

	List<Purchase> findAll() throws RepositoryException;
	Purchase findById(Long id) throws RepositoryException;
	List<Purchase> addAll(List<Purchase> purchases) throws RepositoryException;
	Purchase add(Purchase purchases) throws RepositoryException;
	Purchase update(Purchase purchase) throws RepositoryException;
	boolean delete(Long id) throws RepositoryException;
	
	List<Purchase> findPurchasesByOrder(Long orderId) throws RepositoryException;
	boolean deletePurchasesFromOrder(Long orderId) throws RepositoryException;
	
}
