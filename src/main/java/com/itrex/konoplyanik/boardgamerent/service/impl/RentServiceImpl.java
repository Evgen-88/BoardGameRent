package com.itrex.konoplyanik.boardgamerent.service.impl;

import org.springframework.stereotype.Service;

import com.itrex.konoplyanik.boardgamerent.converters.RentConverter;
import com.itrex.konoplyanik.boardgamerent.dto.RentDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RentSaveDTO;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;
import com.itrex.konoplyanik.boardgamerent.service.RentService;

@Service
public class RentServiceImpl implements RentService {

	private final RentRepository rentRepository;
	private final PurchaseRepository purchaseRepository;
	private final OrderRepository orderRepository;
	
	public RentServiceImpl(RentRepository rentRepository,
			PurchaseRepository purchaseRepository, OrderRepository orderRepository) {
		this.rentRepository = rentRepository;
		this.purchaseRepository = purchaseRepository;
		this.orderRepository = orderRepository;
	}

	@Override
	public RentDTO findById(Long id) throws ServiceException {
		try {
			return RentConverter.convertRentToDTO(rentRepository.findById(id));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findById: " + ex);
		}
	}

	@Override
	public RentDTO add(RentSaveDTO rent) throws ServiceException {
		try {
			return RentConverter.convertRentToDTO(rentRepository.add(RentConverter.convertRentSaveToEntity(rent)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: update: " + ex);
		}
	}

	@Override
	public RentDTO update(RentSaveDTO rent) throws ServiceException {
		try {
			return RentConverter.convertRentToDTO(rentRepository.update(RentConverter.convertRentSaveToEntity(rent)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: update: " + ex);
		}
	}

	@Override
	public boolean delete(Long id) throws ServiceException {
		long orderId = rentRepository.findById(id).getOrder().getId();
		boolean isDeleted = false;
		try {
			isDeleted = rentRepository.delete(id);
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

}
