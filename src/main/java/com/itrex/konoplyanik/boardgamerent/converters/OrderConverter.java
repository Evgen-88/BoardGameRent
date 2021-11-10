package com.itrex.konoplyanik.boardgamerent.converters;

import com.itrex.konoplyanik.boardgamerent.dto.OrderDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderSaveDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserOrdersListDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Order;

public class OrderConverter {

	public static Order convertOrderToEntity(OrderSaveDTO orderDTO) {
		Order order = new Order();
		order.setId(orderDTO.getId());
		order.setUserId(orderDTO.getId());
		order.setTotalPrice(orderDTO.getTotalPrice());
		order.setDate(orderDTO.getDate());
		order.setStatus(orderDTO.getStatus());
		return order;
	}

	public static OrderDTO convertOrderToDTO(Order order) {
		return OrderDTO.builder()
				.id(order.getId())
				.totalPrice(order.getTotalPrice())
				.date(order.getDate())
				.status(order.getStatus())
				.user(UserConverter.convertUserToBaseDTO(order.getUser()))
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
	
	public static UserOrdersListDTO convertOrderToUserListDTO(Order order) {
		return UserOrdersListDTO.builder()
				.id(order.getId())
				.totalPrice(order.getTotalPrice())
				.date(order.getDate())
				.status(order.getStatus())
				.build();
	}
}
