package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itrex.konoplyanik.boardgamerent.converters.PurchaseConverter;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseSaveDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Accessory;
import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.AccessoryRepository;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;
import com.itrex.konoplyanik.boardgamerent.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	private final PurchaseRepository purchaseRepository;
	private final RentRepository rentRepository;
	private final OrderRepository orderRepository;
	private final AccessoryRepository accessoryRepository;

	public PurchaseServiceImpl(PurchaseRepository purchaseRepository, RentRepository rentRepository,
			OrderRepository orderRepository, AccessoryRepository accessoryRepository) {
		this.purchaseRepository = purchaseRepository;
		this.rentRepository = rentRepository;
		this.orderRepository = orderRepository;
		this.accessoryRepository = accessoryRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public PurchaseDTO findById(Long id) throws ServiceException {
		return purchaseRepository.findById(id).map(PurchaseConverter::convertPurchaseToDTO)
				.orElseThrow(() -> new ServiceException("Purchase not found"));
	}

	@Override
	@Transactional
	public PurchaseDTO add(PurchaseSaveDTO purchaseDTO) throws ServiceException {
		Order order = orderRepository.findById(purchaseDTO.getOrderId())
				.orElseThrow(() -> new ServiceException("Order not found"));
		Accessory accessory = accessoryRepository.findById(purchaseDTO.getAccessoryId())
				.orElseThrow(() -> new ServiceException("Accessory not found"));
		Purchase purchase = Purchase.builder().accessory(accessory).order(order).price(purchaseDTO.getPrice())
				.quantity(purchaseDTO.getQuantity()).build();
		return PurchaseConverter.convertPurchaseToDTO(purchaseRepository.save(purchase));
	}

	@Override
	@Transactional
	public PurchaseDTO update(PurchaseSaveDTO purchaseDTO) throws ServiceException {
		Purchase purchase = purchaseRepository.findById(purchaseDTO.getId())
				.orElseThrow(() -> new ServiceException("Purchase not found"));
		if (purchaseDTO.getPrice() != null) {
			purchase.setPrice(purchaseDTO.getPrice());
		}
		if (purchaseDTO.getQuantity() != null) {
			purchase.setQuantity(purchaseDTO.getQuantity());
		}
		purchaseRepository.flush();
		return purchaseRepository.findById(purchaseDTO.getId()).map(PurchaseConverter::convertPurchaseToDTO).get();
	}

	@Override
	@Transactional
	public boolean delete(Long id) throws ServiceException {
		Purchase purchase = purchaseRepository.findById(id)
				.orElseThrow(() -> new ServiceException("Purchase not found"));
		purchaseRepository.delete(purchase);
		if (purchaseRepository.findPurchasesByOrder_id(purchase.getOrder().getId()).size() == 0
				&& rentRepository.findRentsByOrder_id(purchase.getOrder().getId()).size() == 0) {
			orderRepository.delete(purchase.getOrder());
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PurchaseDTO> findPurchasesByOrder(Long orderId) {
		return purchaseRepository.findPurchasesByOrder_id(orderId).stream().map(PurchaseConverter::convertPurchaseToDTO)
				.collect(Collectors.toList());
	}

}
