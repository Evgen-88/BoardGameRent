package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.entity.Status;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;

public class RentRepositoryImplTest extends BaseRepositoryTest {
	
	@Autowired
	private RentRepository repository;
	
	private List<Rent> rents;

	@Before
	public void fill() {
		rents = new ArrayList<>() {{
			add(new Rent(1L, 1L, 1L, LocalDate.of(2021, 10, 23), LocalDate.of(2021, 10, 24), 45));
			add(new Rent(2L, 1L, 2L, LocalDate.of(2021, 10, 23), LocalDate.of(2021, 10, 25), 90));
			add(new Rent(3L, 3L, 3L, LocalDate.of(2021, 10, 22), LocalDate.of(2021, 10, 24), 100));
			add(new Rent(4L, 2L, 4L, LocalDate.of(2021, 10, 25), LocalDate.of(2021, 10, 28), 120));
		}};
	}
	
	@Test
	public void findAll_validData_shouldReturnAllRents() {
		//given && when
		List<Rent>	actual = repository.findAll();
		//then
		Assert.assertEquals(rents, actual);
	}
	
	@Test
	public void findById_validData_shouldReturnRentById() {
		// given
		Rent expected = rents.get(0);
		// when
		Rent actual = repository.findById(expected.getId());
		// then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void addAll_validData_shouldAddAllRents() {
		//given
		List<Rent> expected = new ArrayList<>() {{
			add(new Rent(5L, 4L, 4L, LocalDate.of(2021, 10, 26), LocalDate.of(2021, 10, 27), 50));
			add(new Rent(6L, 1L, 4L, LocalDate.of(2021, 10, 27), LocalDate.of(2021, 10, 28), 45));
		}};
		//when
		List<Rent> rents = new ArrayList<>() {{
			add(new Rent(4L, 4L, LocalDate.of(2021, 10, 26), LocalDate.of(2021, 10, 27), 50));
			add(new Rent(1L, 4L, LocalDate.of(2021, 10, 27), LocalDate.of(2021, 10, 28), 45));
		}};
		List<Rent> actual = repository.addAll(rents);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void add_validData_shouldAddRent() {
		//given
		Rent expected = new Rent(5L, 4L, 4L, LocalDate.of(2021, 10, 26), LocalDate.of(2021, 10, 27), 50);
		Rent rent = new Rent(4L, 4L, LocalDate.of(2021, 10, 26), LocalDate.of(2021, 10, 27), 50);
		//when
		Rent actual = repository.add(rent);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void update_validData_shouldUpdateRent() {
		//given
		Rent rent = rents.get(0);
		Rent expected = new Rent(1L, 1L, 1L, LocalDate.of(2021, 10, 24), LocalDate.of(2021, 10, 25), 50);
		//when
		rent.setRentFrom(LocalDate.of(2021, 10, 24));
		rent.setRentTo(LocalDate.of(2021, 10, 25));
		rent.setPrice(50);
		Rent actual = repository.update(rent);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void delete_validData_shouldDeleteRent() {
		//given
		Rent rent = rents.get(1);
		//when
		boolean actual = repository.delete(rent.getId());
		//then
		Assert.assertTrue(actual);
	}
	
	@Test
	public void findRentsByOrder_validData_shouldReturnAllRents() {
		//given
		List<Rent> expected = new ArrayList<>() {{
			add(rents.get(0));
		}};
		//when
		List<Rent> actual = repository.findRentsByOrder(1L);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void deleteRentsFromOrder_shouldDeleteRent() {
		//given
		Order order = new Order(3L, 2L, 126, LocalDate.of(2021, 10, 23), Status.confirmed);
		//when
		boolean actual = repository.deleteRentsFromOrder(order.getId());
		//then
		Assert.assertTrue(actual);
		Assert.assertEquals(new ArrayList<>(), repository.findRentsByOrder(order.getId()));
	}

}
