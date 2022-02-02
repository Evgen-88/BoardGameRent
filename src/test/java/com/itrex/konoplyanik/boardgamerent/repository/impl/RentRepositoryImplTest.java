package com.itrex.konoplyanik.boardgamerent.repository.impl;

import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentRepositoryImplTest extends BaseRepositoryTest {
	
	@Autowired
	private RentRepository repository;
	
	private static List<Rent> rents;

	@BeforeAll
	public static void fill() {
		rents = new ArrayList<>() {{
			add(Rent.builder().id(1L).rentFrom(LocalDate.of(2021, 10, 23)).rentTo(LocalDate.of(2021, 10, 24)).price(45).build());
			add(Rent.builder().id(2L).rentFrom(LocalDate.of(2021, 10, 23)).rentTo(LocalDate.of(2021, 10, 25)).price(90).build());
			add(Rent.builder().id(3L).rentFrom(LocalDate.of(2021, 10, 22)).rentTo(LocalDate.of(2021, 10, 24)).price(100).build());
			add(Rent.builder().id(4L).rentFrom(LocalDate.of(2021, 10, 25)).rentTo(LocalDate.of(2021, 10, 28)).price(120).build());
		}};
	}
	
	@Test
	public void findAll_validData_shouldReturnAllRents() {
		//given && when
		List<Rent>	actual = repository.findAll();
		//then
		Assertions.assertEquals(rents, actual);
	}
	
	@Test
	public void findById_validData_shouldReturnRentById() {
		// given
		Rent expected = rents.get(0);
		// when
		Rent actual = repository.findById(expected.getId()).get();
		// then
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void add_validData_shouldAddRent() {
		//given
		Rent expected = Rent.builder().id(5L).rentFrom(LocalDate.of(2021, 10, 26)).rentTo(LocalDate.of(2021, 10, 27)).price(50).build();
		Rent rent = Rent.builder().rentFrom(LocalDate.of(2021, 10, 26)).rentTo(LocalDate.of(2021, 10, 27)).price(50).build();
		//when
		Rent actual = repository.save(rent);
		//then
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void update_validData_shouldUpdateRent() {
		//given
		Rent rent = rents.get(0);
		Rent expected = Rent.builder().id(1L).rentFrom(LocalDate.of(2021, 10, 24)).rentTo(LocalDate.of(2021, 10, 25)).price(50).build();
		Assertions.assertEquals(expected.getId(), rent.getId());
		//when
		Rent actual = repository.save(Rent.builder().id(1L).rentFrom(LocalDate.of(2021, 10, 24)).rentTo(LocalDate.of(2021, 10, 25)).price(50).build());
		//then
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void findRentsByOrder_validData_shouldReturnAllRents() {
		//given
		List<Rent> expected = new ArrayList<>() {{
			add(rents.get(0));
		}};
		//when
		List<Rent> actual = repository.findRentsByOrder_id(1L);
		//then
		Assertions.assertEquals(expected, actual);
	}

}
