package com.itrex.konoplyanik.boardgamerent.converters;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.itrex.konoplyanik.boardgamerent.dto.OrderDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderSaveDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListForUserDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.User;

public class OrderConverter {

	public static Order convertOrderToEntity(OrderSaveDTO orderDTO) {
		return Order.builder()
				.id(orderDTO.getId())
				.user(User.builder().id(orderDTO.getUserId()).build())
				.totalPrice(orderDTO.getTotalPrice())
				.date(orderDTO.getDate())
				.status(orderDTO.getStatus())
				.build();
	}

	public static OrderDTO convertOrderToDTO(Order order) {
		return OrderDTO.builder()
				.id(order.getId())
				.totalPrice(order.getTotalPrice())
				.date(order.getDate())
				.status(order.getStatus())
				.user(UserConverter.convertUserToBaseDTO(order.getUser()))
				.purchases(PurchaseConverter.convertSetPurchasesToDTO(order.getPurchases()))
				.rents(RentConverter.convertSetRentsToDTO(order.getRents()))
				.build();
	}
	
	public static OrderSaveDTO convertOrderToSaveDTO(Order order) {
		return OrderSaveDTO.builder()
				.id(order.getId())
				.totalPrice(order.getTotalPrice())
				.date(order.getDate())
				.status(order.getStatus())
				.userId(order.getUser().getId())
				.build();
	}
	
	public static OrderListDTO convertOrderToListDTO(Order order) {
		return OrderListDTO.builder()
				.id(order.getId())
				.totalPrice(order.getTotalPrice())
				.date(order.getDate())
				.status(order.getStatus())
				.user(UserConverter.convertUserToBaseDTO(order.getUser()))
				.build();
	}
	
	public static OrderListForUserDTO convertOrderToListForUserDTO(Order order) {
		return OrderListForUserDTO.builder()
				.id(order.getId())
				.totalPrice(order.getTotalPrice())
				.date(order.getDate())
				.status(order.getStatus())
				.build();
	}
	
	public static List<OrderListForUserDTO> convertToOrderListForUserDTO(Set<Order> orders) {
		List<OrderListForUserDTO> ordersFOrUser = new ArrayList<>();
		for (Order order : orders) {
			ordersFOrUser.add(convertOrderToListForUserDTO(order));
		}
		return ordersFOrUser;
	}
}
