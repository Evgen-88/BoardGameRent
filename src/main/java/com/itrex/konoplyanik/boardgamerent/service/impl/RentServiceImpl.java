package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itrex.konoplyanik.boardgamerent.converters.RentConverter;
import com.itrex.konoplyanik.boardgamerent.dto.RentDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RentSaveDTO;
import com.itrex.konoplyanik.boardgamerent.entity.BoardGame;
import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.BoardGameRepository;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;
import com.itrex.konoplyanik.boardgamerent.service.RentService;

@Service
public class RentServiceImpl implements RentService {

	private final RentRepository rentRepository;
	private final PurchaseRepository purchaseRepository;
	private final OrderRepository orderRepository;
	private final BoardGameRepository boardGameRepository;

	public RentServiceImpl(RentRepository rentRepository, PurchaseRepository purchaseRepository,
			OrderRepository orderRepository, BoardGameRepository boardGameRepository) {
		this.rentRepository = rentRepository;
		this.purchaseRepository = purchaseRepository;
		this.orderRepository = orderRepository;
		this.boardGameRepository = boardGameRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public RentDTO findById(Long id) throws ServiceException {
		return rentRepository.findById(id).map(RentConverter::convertRentToDTO)
				.orElseThrow(() -> new ServiceException("Rent not found"));
	}

	@Override
	@Transactional
	public RentDTO add(RentSaveDTO rentDTO) throws ServiceException {
		Order order = orderRepository.findById(rentDTO.getOrderId())
				.orElseThrow(() -> new ServiceException("Order not found"));
		BoardGame boardGame = boardGameRepository.findById(rentDTO.getBoardGameId())
				.orElseThrow(() -> new ServiceException("BoardGame not found"));
		Rent rent = Rent.builder().boardGame(boardGame).order(order).rentFrom(rentDTO.getRentFrom())
				.rentTo(rentDTO.getRentTo()).price(rentDTO.getPrice()).build();
		return RentConverter.convertRentToDTO(rentRepository.save(rent));
	}

	@Override
	@Transactional
	public RentDTO update(RentSaveDTO rentDTO) throws ServiceException {
		Rent rent = rentRepository.findById(rentDTO.getId()).orElseThrow(() -> new ServiceException("Rent not found"));
		if (rentDTO.getRentFrom() != null) {
			rent.setRentFrom(rentDTO.getRentFrom());
		}
		if (rentDTO.getRentTo() != null) {
			rent.setRentTo(rentDTO.getRentTo());
		}
		if (rentDTO.getPrice() != null) {
			rent.setPrice(rentDTO.getPrice());
		}
		rentRepository.flush();
		return rentRepository.findById(rentDTO.getId()).map(RentConverter::convertRentToDTO).get();
	}

	@Override
	@Transactional
	public boolean delete(Long id) throws ServiceException {
		Rent rent = rentRepository.findById(id).orElseThrow(() -> new ServiceException("Rent not found"));
		rentRepository.delete(rent);
		if (purchaseRepository.findPurchasesByOrder_id(rent.getOrder().getId()).size() == 0
				&& rentRepository.findRentsByOrder_id(rent.getOrder().getId()).size() == 0) {
			orderRepository.delete(rent.getOrder());
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public List<RentDTO> findRentsByOrder(Long orderId) throws ServiceException {
		return rentRepository.findRentsByOrder_id(orderId).stream().map(RentConverter::convertRentToDTO)
				.collect(Collectors.toList());
	}
}
