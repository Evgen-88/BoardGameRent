package com.itrex.konoplyanik.boardgamerent.repository.hibernate;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;

@Deprecated
public interface HibernateOrderRepository {

	List<Order> findAll() throws RepositoryException;
	Order findById(Long id) throws RepositoryException;
	Order add(Order order) throws RepositoryException;
	Order update(Order order) throws RepositoryException;
	boolean delete(Long id) throws RepositoryException;
	
	List<Order> findOrdersByUser(Long userId) throws RepositoryException;
	boolean deleteOrdersByUser(Long userId) throws RepositoryException;
	
}
