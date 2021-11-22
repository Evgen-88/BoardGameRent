package com.itrex.konoplyanik.boardgamerent.repository;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;

public interface RentRepository {
	
	List<Rent> findAll() throws RepositoryException;
	Rent findById(Long id) throws RepositoryException;
	Rent add(Rent rent) throws RepositoryException;
	Rent update(Rent rent) throws RepositoryException;
	boolean delete(Long id) throws RepositoryException;
	
	List<Rent> findRentsByOrder(Long orderId) throws RepositoryException;
	boolean deleteRentsFromOrder(Long orderId) throws RepositoryException;
}
