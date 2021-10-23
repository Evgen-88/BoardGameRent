package com.itrex.konoplyanik.boardgamerent.repository;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;

public interface OrderRepository {

	List<Order> findAll() throws RepositoryException;
	Order findById(Long id) throws RepositoryException;
	List<Order> addAll(List<Order> orders) throws RepositoryException;
	Order add(Order order) throws RepositoryException;
	Order update(Order order) throws RepositoryException;
	boolean delete(Long id) throws RepositoryException;
	
	List<Order> findOrdersByUser(Long userId) throws RepositoryException;
	boolean deleteOrdersByUser(Long userId) throws RepositoryException;
	
}
