package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itrex.konoplyanik.boardgamerent.converters.OrderConverter;
import com.itrex.konoplyanik.boardgamerent.converters.PurchaseConverter;
import com.itrex.konoplyanik.boardgamerent.converters.RentConverter;
import com.itrex.konoplyanik.boardgamerent.dto.OrderDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderSaveDTO;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RentDTO;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;
import com.itrex.konoplyanik.boardgamerent.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final PurchaseRepository purchaseRepository;
	private final RentRepository rentRepository;
	
	public OrderServiceImpl(OrderRepository orderRepository, PurchaseRepository purchaseRepository, RentRepository rentRepository) {
		this.orderRepository = orderRepository;
		this.purchaseRepository = purchaseRepository;
		this.rentRepository = rentRepository;
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
			order.setPurchases(PurchaseConverter.convertPurchasesToDTO(purchaseRepository.findPurchasesByOrder(id)));
			order.setRents(RentConverter.convertRentsToDTO(rentRepository.findRentsByOrder(id)));
			return order;
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findById: " + ex);
		}
	}

	@Override
	public OrderDTO add(OrderSaveDTO order) throws ServiceException {
		try {
			return OrderConverter.convertOrderToDTO(orderRepository.add(OrderConverter.convertOrderToEntity(order)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: add: " + ex);
		}
	}

	@Override
	public OrderDTO update(OrderSaveDTO order) throws ServiceException {
		try {
			return OrderConverter.convertOrderToDTO(orderRepository.update(OrderConverter.convertOrderToEntity(order)));
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
	public List<PurchaseDTO> findPurchasesByOrder(Long orderId) throws ServiceException {
		try {
			return purchaseRepository.findPurchasesByOrder(orderId).stream()
					.map(PurchaseConverter::convertPurchaseToDTO)
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findPurchasesByOrder: " + ex);
		}
	}
	
	@Override
	public List<RentDTO> findRentsByOrder(Long orderId) throws ServiceException {
		try {
			return rentRepository.findRentsByOrder(orderId).stream()
					.map(RentConverter::convertRentToDTO)
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findRentsByOrder: " + ex);
		}
	}

}
