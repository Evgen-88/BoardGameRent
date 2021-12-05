package com.itrex.konoplyanik.boardgamerent.repository.hibernate;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;

@Deprecated
public interface HibernateRentRepository {
	
	List<Rent> findAll() throws RepositoryException;
	Rent findById(Long id) throws RepositoryException;
	Rent add(Rent rent) throws RepositoryException;
	Rent update(Rent rent) throws RepositoryException;
	boolean delete(Long id) throws RepositoryException;
	
	List<Rent> findRentsByOrder(Long orderId) throws RepositoryException;
	boolean deleteRentsFromOrder(Long orderId) throws RepositoryException;
}
