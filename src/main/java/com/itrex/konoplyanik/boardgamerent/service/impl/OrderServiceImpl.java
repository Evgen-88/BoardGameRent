package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itrex.konoplyanik.boardgamerent.converters.OrderConverter;
import com.itrex.konoplyanik.boardgamerent.dto.OrderDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderSaveDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.entity.User;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;
import com.itrex.konoplyanik.boardgamerent.repository.UserRepository;
import com.itrex.konoplyanik.boardgamerent.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final PurchaseRepository purchaseRepository;
	private final RentRepository rentRepository;

	@Override
	@Transactional(readOnly = true)
	public List<OrderListDTO> findAll() {
		return orderRepository.findAll().stream().map(OrderConverter::convertOrderToListDTO)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) throws ServiceException {
		return orderRepository.findById(id).map(OrderConverter::convertOrderToDTO)
				.orElseThrow(() -> new ServiceException("Order not found"));
	}

	@Override
	@Transactional
	public OrderSaveDTO add(OrderSaveDTO orderDTO) throws ServiceException {
		User user = userRepository.findById(orderDTO.getUserId())
				.orElseThrow(() -> new ServiceException("User not found"));
		Order order = Order.builder().user(user).date(orderDTO.getDate()).status(orderDTO.getStatus())
				.totalPrice(orderDTO.getTotalPrice()).build();
		return OrderConverter.convertOrderToSaveDTO(orderRepository.save(order));
	}

	@Override
	@Transactional
	public OrderSaveDTO update(OrderSaveDTO orderDTO) throws ServiceException {
		Order order = orderRepository.findById(orderDTO.getId())
				.orElseThrow(() -> new ServiceException("Order not found"));
		if (orderDTO.getDate() != null) {
			order.setDate(orderDTO.getDate());
		}
		if (orderDTO.getStatus() != null) {
			order.setStatus(orderDTO.getStatus());
		}
		if (orderDTO.getTotalPrice() != null) {
			order.setTotalPrice(orderDTO.getTotalPrice());
		}
		orderRepository.flush();
		return orderRepository.findById(order.getId()).map(OrderConverter::convertOrderToSaveDTO).get();
	}

	@Override
	@Transactional
	public boolean delete(Long id) throws ServiceException {
		Order order = orderRepository.findOrderById(id).orElseThrow(() -> new ServiceException("Order not found"));
		if (order.getPurchases() != null && !order.getPurchases().isEmpty()) {
			for (Purchase purchase : order.getPurchases()) {
				purchaseRepository.delete(purchase);
			}
		}
		if (order.getRents() != null && !order.getRents().isEmpty()) {
			for (Rent rent : order.getRents()) {
				rentRepository.delete(rent);
			}
		}
		orderRepository.delete(order);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrderListDTO> findOrdersByUser(Long userId) {
		return orderRepository.findOrdersByUser_id(userId).stream().map(OrderConverter::convertOrderToListDTO)
				.collect(Collectors.toList());
	}

}
