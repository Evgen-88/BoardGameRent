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
			add(new Accessory(1L, "Протекторы для карт 65х87", 12, 24));
			add(new Accessory(2L, "Протекторы для карт 48х64", 13, 20));
			add(new Accessory(3L, "Протекторы для карт 102х143", 18, 11));
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
		Accessory expected = accessories.get(0);
		//when
		Accessory actual = repository.findById(expected.getId());
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void addAll_validData_shouldAddAllAccessories() {
		//given
		List<Accessory> expected = new ArrayList<>() {{
			add(new Accessory(4L, "Кубик D6", 6, 52));
			add(new Accessory(5L, "Кубик D20", 8, 34));
		}};
		
		List<Accessory> accessories = new ArrayList<>() {{
			add(new Accessory("Кубик D6", 6, 52));
			add(new Accessory("Кубик D20", 8, 34));
		}};
		//when
		List<Accessory> actual = repository.addAll(accessories);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void add_validData_shouldAddAccessory() {
		//given
		Accessory expected = new Accessory(4L, "Dicetray", 24, 8);
		Accessory accessory = new Accessory("Dicetray", 24, 8);
		//when
		Accessory actual = repository.add(accessory);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void update_validData_shouldUpdateAccessory() {
		//given
		Accessory accessory = accessories.get(0);
		Accessory expected = new Accessory(1L, "Протекторы для карт 68х92", 14, 21);
		//when
		accessory.setName("Протекторы для карт 68х92");
		accessory.setPrice(14);
		accessory.setQuantity(21);
		Accessory actual = repository.update(accessory);
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
