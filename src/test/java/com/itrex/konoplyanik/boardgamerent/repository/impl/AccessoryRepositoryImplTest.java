package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.itrex.konoplyanik.boardgamerent.entity.Accessory;
import com.itrex.konoplyanik.boardgamerent.repository.AccessoryRepository;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;

public class AccessoryRepositoryImplTest extends BaseRepositoryTest {
	
	@Autowired
	private AccessoryRepository repository;
	
	private List<Accessory> accessories;
	
	
	@Before
	public void fill() {
		accessories = new ArrayList<>() {{
			add(Accessory.builder().id(1L).name("Протекторы для карт 65х87").price(12).quantity(24).build());
			add(Accessory.builder().id(2L).name("Протекторы для карт 48х64").price(13).quantity(20).build());
			add(Accessory.builder().id(3L).name("Протекторы для карт 102х143").price(18).quantity(11).build());
		}};
	}
	
	@Test
	public void findAll_validData_shouldReturnAllAccessories() {
		//given && when
		List<Accessory>	actual = repository.findAll();
		//then
		Assert.assertEquals(accessories, actual);
	}
	
	@Test
	public void findById_validData_shouldReturnAccessoryById() {
		//given
		Accessory expected = accessories.get(1);
		//when
		Accessory actual = repository.findById(expected.getId());
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void add_validData_shouldAddAccessory() {
		//given
		Accessory expected = Accessory.builder().id(4L).name("Кубик D6").price(6).quantity(52).build();
		Accessory accessory = Accessory.builder().name("Кубик D6").price(6).quantity(52).build();
		//when
		Accessory actual = repository.add(accessory);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void update_validData_shouldUpdateAccessory() {
		//given
		Accessory accessory = accessories.get(0);
		Accessory expected = Accessory.builder().id(1L).name("Протекторы для карт 68х92").price(14).quantity(21).build();
		Assert.assertEquals(accessory.getId(), expected.getId());
		//when
		
		Accessory actual = repository.update(Accessory.builder().id(1L).name("Протекторы для карт 68х92").price(14).quantity(21).build());
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void delete_validData_shouldDeleteAccessory() {
		//given
		Accessory accessory = accessories.get(0);
		//when
		boolean actual = repository.delete(accessory.getId());
		//then
		Assert.assertTrue(actual);
	}
	
}
