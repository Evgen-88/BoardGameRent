package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itrex.konoplyanik.boardgamerent.converters.OrderConverter;
import com.itrex.konoplyanik.boardgamerent.dto.OrderDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListForUserDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderSaveDTO;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.service.OrderService;

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
					.map(OrderConverter::convertOrderToListDTO)
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findAll: " + ex);
		}
	}

	@Override
	public OrderDTO findById(Long id) throws ServiceException {
		try {
			OrderDTO order = OrderConverter.convertOrderToDTO(orderRepository.findById(id));
			return order;
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findById: " + ex);
		}
	}

	@Override
	public OrderSaveDTO add(OrderSaveDTO order) throws ServiceException {
		try {
			return OrderConverter.convertOrderToSaveDTO(orderRepository.add(OrderConverter.convertOrderToEntity(order)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: add: " + ex);
		}
	}
	
	@Override
	public OrderSaveDTO update(OrderSaveDTO order) throws ServiceException {
		try {
			return OrderConverter.convertOrderToSaveDTO(orderRepository.update(OrderConverter.convertOrderToEntity(order)));
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
					.map(OrderConverter::convertOrderToListDTO)
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findOrdersByUser: " + ex);
		}
	}

	@Override
	public List<OrderListForUserDTO> findOrderListForUserByUser(Long userId) throws ServiceException {
		try {
			return orderRepository.findOrdersByUser(userId).stream()
					.map(OrderConverter::convertOrderToListForUserDTO)
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findOrdersByUser: " + ex);
		}
	}


}
