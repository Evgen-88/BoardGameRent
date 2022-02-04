package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Status;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;

public class OrderRepositoryImplTest extends BaseRepositoryTest {
	
	@Autowired
	private OrderRepository repository;
	
	private static List<Order> orders;

	@BeforeAll
	public static void fill() {
		orders = new ArrayList<>() {{
			add(Order.builder().id(1L).totalPrice(45).date(LocalDate.of(2021, 10, 23)).status(Status.confirmed).build());
			add(Order.builder().id(2L).totalPrice(90).date(LocalDate.of(2021, 10, 23)).status(Status.confirmed).build());
			add(Order.builder().id(3L).totalPrice(126).date(LocalDate.of(2021, 10, 23)).status(Status.confirmed).build());
			add(Order.builder().id(4L).totalPrice(120).date(LocalDate.of(2021, 10, 22)).status(Status.booked).build());
		}};
	}
	
	@Test
	public void findAll_validData_shouldReturnAllOrder() {
		// given && when
		List<Order> actual = repository.findAll();
		// then
		Assertions.assertEquals(orders, actual);
	}

	@Test
	public void findById_validData_shouldReturnOrderById() {
		// given
		Order expected = orders.get(0);
		// when
		Order actual = repository.findById(expected.getId()).get();
		// then
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void add_validData_shouldAddOrder() {
		// given
		Order expected = Order.builder().id(5L).totalPrice(90).date(LocalDate.of(2021, 10, 21)).status(Status.rejected).build();
		Order order = Order.builder().totalPrice(90).date(LocalDate.of(2021, 10, 21)).status(Status.rejected).build();
		// when
		Order actual = repository.save(order);
		// then
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void update_validData_shouldUpdateOrder() {
		// given
		Order order = orders.get(0);
		Order expected = Order.builder().id(1L).totalPrice(88).date(LocalDate.of(2021, 10, 28)).status(Status.rejected).build();
		Assertions.assertEquals(expected.getId(), order.getId());
		// when
		Order actual = repository.save(expected);
		// then
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void findOrdersByUser_validData_shouldAddAllOrders() {
		// given
		List<Order> expected = new ArrayList<>() {{
			add(orders.get(0));
		}};
		// when
		List<Order> actual = repository.findOrdersByUser_id(1L);
		// then
		Assertions.assertEquals(expected, actual);
	}

}
