package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itrex.konoplyanik.boardgamerent.dto.OrderDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.service.OrderService;
import com.itrex.konoplyanik.boardgamerent.util.Converter;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public List<OrderListDTO> findAll() throws ServiceException {
		try {
			return orderRepository.findAll().stream()
					.map(order -> Converter.convertOrderToListDTO(order))
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findAll: " + ex);
		}
	}

	@Override
	public OrderDTO findById(Long id) throws ServiceException {
		try {
			return Converter.convertOrderToDTO(orderRepository.findById(id));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findById: " + ex);
		}
	}

	@Override
	public OrderDTO add(OrderDTO order) throws ServiceException {
		try {
			return Converter.convertOrderToDTO(orderRepository.add(Converter.convertOrderToEntity(order)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: add: " + ex);
		}
	}

	@Override
	public OrderDTO update(OrderDTO order) throws ServiceException {
		try {
			return Converter.convertOrderToDTO(orderRepository.update(Converter.convertOrderToEntity(order)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: update: " + ex);
		}
	}

	@Override
	public boolean delete(Long id) throws ServiceException {
		try {
			return orderRepository.delete(id);
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: update: " + ex);
		}
	}

	@Override
	public List<OrderListDTO> findOrdersByUser(Long userId) throws ServiceException {
		try {
			return orderRepository.findOrdersByUser(userId).stream()
					.map(order -> Converter.convertOrderToListDTO(order))
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findOrdersByUser: " + ex);
		}
	}

}
