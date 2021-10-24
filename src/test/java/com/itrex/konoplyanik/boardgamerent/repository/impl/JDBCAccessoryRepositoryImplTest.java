package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.itrex.konoplyanik.boardgamerent.entity.Accessory;
import com.itrex.konoplyanik.boardgamerent.repository.AccessoryRepository;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;

public class JDBCAccessoryRepositoryImplTest extends BaseRepositoryTest {
	private final AccessoryRepository repository;
	private List<Accessory> accessories;
	
	public JDBCAccessoryRepositoryImplTest() {
		super();
		repository = new JDBCAccessoryRepositoryImpl(getConnectionPool());
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
		//when
		List<Accessory> actual = new ArrayList<>() {{
			add(new Accessory("Кубик D6", 6, 52));
			add(new Accessory("Кубик D20", 8, 34));
		}};
		repository.addAll(actual);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void add_validData_shouldAddAccessory() {
		//given
		Accessory expected = new Accessory(5L, "Dicetray", 24, 8);
		//when
		Accessory actual = new Accessory("Dicetray", 24, 8);
		repository.add(actual);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void update_validData_shouldUpdateAccessory() {
		//given
		Accessory actual = accessories.get(0);
		Accessory expected = new Accessory(1L, "Протекторы для карт 68х92", 14, 21);
		//when
		actual.setName("Протекторы для карт 68х92");
		actual.setPrice(14);
		actual.setQuantity(21);
		repository.update(actual);
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
