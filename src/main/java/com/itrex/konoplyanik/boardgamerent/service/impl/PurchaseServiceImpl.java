package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itrex.konoplyanik.boardgamerent.dto.PurchaseDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;
import com.itrex.konoplyanik.boardgamerent.service.PurchaseService;
import com.itrex.konoplyanik.boardgamerent.util.Converter;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	private final PurchaseRepository purchaseRepository;
	private final OrderRepository orderRepository;
	
	public PurchaseServiceImpl(PurchaseRepository purchaseRepository,
			OrderRepository orderRepository) {
		this.purchaseRepository = purchaseRepository;
		this.orderRepository = orderRepository;
	}

	@Override
	public PurchaseDTO findById(Long id) throws ServiceException {
		try {
			return Converter.convertPurchaseToDTO(purchaseRepository.findById(id));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findById: " + ex);
		}
	}

	@Override
	public PurchaseDTO add(PurchaseDTO purchase) throws ServiceException {
		try {
			return Converter.convertPurchaseToDTO(purchaseRepository.add(Converter.convertPurchaseToEntity(purchase)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: add: " + ex);
		}
	}

	@Override
	public PurchaseDTO update(PurchaseDTO purchase) throws ServiceException {
		try {
			return Converter.convertPurchaseToDTO(purchaseRepository.update(Converter.convertPurchaseToEntity(purchase)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: update: " + ex);
		}
	}

	@Override
	public boolean delete(Long id) throws ServiceException {
		Order order = orderRepository.findById(purchaseRepository.findById(id).getOrderId());
		try {
			return purchaseRepository.delete(id);
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: update: " + ex);
		} finally {
			if (order.getPurchases().size() == 0 && order.getRents().size() == 0) {
				orderRepository.delete(order.getId());
			}
		}
	}

	@Override
	public List<PurchaseDTO> findPurchasesByOrder(Long orderId) throws ServiceException {
		try {
			return purchaseRepository.findPurchasesByOrder(orderId).stream()
					.map(purchase -> Converter.convertPurchaseToDTO(purchase))
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findPurchasesByOrder: " + ex);
		}
	}

}
