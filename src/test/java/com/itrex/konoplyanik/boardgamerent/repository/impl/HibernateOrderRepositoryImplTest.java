package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Status;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;

public class HibernateOrderRepositoryImplTest extends BaseRepositoryTest {
	private final OrderRepository repository;
	private List<Order> orders;

	public HibernateOrderRepositoryImplTest() {
		super();
		repository = new HibernateOrderRepositoryImpl(getSessionFactory().openSession());
	}
	
	@Before
	public void fill() {
		orders = new ArrayList<>() {{
			add(new Order(1L, 1L, 45, LocalDate.of(2021, 10, 23), Status.confirmed));
			add(new Order(2L, 4L, 90, LocalDate.of(2021, 10, 23), Status.confirmed));
			add(new Order(3L, 2L, 126, LocalDate.of(2021, 10, 23), Status.confirmed));
			add(new Order(4L, 4L, 120, LocalDate.of(2021, 10, 22), Status.booked));
		}};
	}
	
	@Test
	public void findAll_validData_shouldReturnAllOrder() {
		// given && when
		List<Order> actual = repository.findAll();
		// then
		Assert.assertEquals(orders, actual);
	}

	@Test
	public void findById_validData_shouldReturnOrderById() {
		// given
		Order expected = orders.get(0);
		// when
		Order actual = repository.findById(expected.getId());
		// then
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void addAll_validData_shouldAddAllOrders() {
		// given
		List<Order> expected = new ArrayList<>() {
			{
				add(new Order(5L, 2L, 55, LocalDate.of(2021, 10, 24), Status.booked));
				add(new Order(6L, 5L, 66, LocalDate.of(2021, 10, 25), Status.confirmed));
			}
		};
		// when
		List<Order> orders = new ArrayList<>() {
			{
				add(new Order(2L, 55, LocalDate.of(2021, 10, 24), Status.booked));
				add(new Order(5L, 66, LocalDate.of(2021, 10, 25), Status.confirmed));
			}
		};
		List<Order> actual = repository.addAll(orders);
		// then
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void add_validData_shouldAddOrder() {
		// given
		Order expected = new Order(5L, 4L, 90, LocalDate.of(2021, 10, 21), Status.rejected);
		Order order = new Order(4L, 90, LocalDate.of(2021, 10, 21), Status.rejected);
		// when
		Order actual = repository.add(order);
		// then
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void update_validData_shouldUpdateOrder() {
		// given
		Order order = orders.get(0);
		Order expected = new Order(1L, 1L, 88, LocalDate.of(2021, 10, 28), Status.rejected);
		// when
		order.setId(1L);
		order.setUserId(1L);
		order.setTotalPrice(88);
		order.setDate(LocalDate.of(2021, 10, 28));
		order.setStatus(Status.rejected);
		Order actual = repository.update(order);
		// then
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void delete_validData_shouldDeleteOrder() {
		// given
		Order order = orders.get(1);
		// when
		boolean actual = repository.delete(order.getId());
		// then
		Assert.assertTrue(actual);
	}

	@Test
	public void findOrdersByUser_validData_shouldAddAllOrders() {
		// given
		List<Order> expected = new ArrayList<>() {{
			add(new Order(4L, 4L, 120, LocalDate.of(2021, 10, 22), Status.booked));
			add(new Order(2L, 4L, 90, LocalDate.of(2021, 10, 23), Status.confirmed));
		}};
		// when
		List<Order> actual = repository.findOrdersByUser(4L);
		// then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void deleteOrdersByUser_validData_shouldDeleteOrders() {
		// given
				Order order = orders.get(2);
				// when
				boolean actual = repository.deleteOrdersByUser(order.getUserId());
				// then
				Assert.assertTrue(actual);
	}

}
