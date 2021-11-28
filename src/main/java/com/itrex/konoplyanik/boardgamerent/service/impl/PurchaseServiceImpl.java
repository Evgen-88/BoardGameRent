package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itrex.konoplyanik.boardgamerent.converters.PurchaseConverter;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseSaveDTO;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;
import com.itrex.konoplyanik.boardgamerent.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	private final PurchaseRepository purchaseRepository;
	private final RentRepository rentRepository;
	private final OrderRepository orderRepository;
	
	public PurchaseServiceImpl(PurchaseRepository purchaseRepository,
			RentRepository rentRepository, OrderRepository orderRepository) {
		this.purchaseRepository = purchaseRepository;
		this.rentRepository = rentRepository;
		this.orderRepository = orderRepository;
	}

	@Override
	public PurchaseDTO findById(Long id) throws ServiceException {
		try {
			return PurchaseConverter.convertPurchaseToDTO(purchaseRepository.findById(id));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findById: " + ex);
		}
	}

	@Override
	public PurchaseDTO add(PurchaseSaveDTO purchase) throws ServiceException {
		try {
			return PurchaseConverter.convertPurchaseToDTO(purchaseRepository.add(PurchaseConverter.convertPurchaseToEntity(purchase)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: add: " + ex);
		}
	}

	@Override
	public PurchaseDTO update(PurchaseSaveDTO purchase) throws ServiceException {
		try {
			return PurchaseConverter.convertPurchaseToDTO(purchaseRepository.update(PurchaseConverter.convertPurchaseToEntity(purchase)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: update: " + ex);
		}
	}

	@Override
	public boolean delete(Long id) throws ServiceException {
		long orderId = purchaseRepository.findById(id).getOrder().getId();
		boolean isDeleted = false;
		try {
			isDeleted = purchaseRepository.delete(id);
			if (isDeleted) {
				if (purchaseRepository.findPurchasesByOrder(orderId).size() == 0 && rentRepository.findRentsByOrder(orderId).size() == 0) {
					orderRepository.delete(orderId);
				}
			}
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: delete: " + ex);
		}
		return isDeleted;
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

}
