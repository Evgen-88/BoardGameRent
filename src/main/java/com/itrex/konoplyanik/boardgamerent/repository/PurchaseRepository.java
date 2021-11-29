package com.itrex.konoplyanik.boardgamerent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

	List<Purchase> findPurchasesByOrder_id(Long id) throws RepositoryException;
}
