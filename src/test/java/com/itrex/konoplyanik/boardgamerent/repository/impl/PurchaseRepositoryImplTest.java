package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.entity.Status;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;

@SpringBootTest
public class PurchaseRepositoryImplTest extends BaseRepositoryTest {
	
	@Autowired
	private PurchaseRepository repository;
	
	private List<Purchase> purchases;

	@Before
	public void fill() {
		purchases = new ArrayList<>() {{
			add(Purchase.builder().id(1L).quantity(2).price(26).build());
			add(Purchase.builder().id(2L).quantity(3).price(36).build());
		}};
	}
	
	@Test
	public void findAll_validData_shouldReturnAllPurchases() {
		//given && when
		List<Purchase>	actual = repository.findAll();
		//then
		Assert.assertEquals(purchases, actual);
	}
	
	@Test
	public void findById_validData_shouldReturnPurchaseById() {
		//given
		Purchase expected = purchases.get(0);
		//when
		Purchase actual = repository.findById(expected.getId()).get();
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void add_validData_shouldAddPurchase() {
		//given
		Purchase expected = Purchase.builder().id(3L).quantity(1).price(18).build();
		Purchase purchase = Purchase.builder().quantity(1).price(18).build();
		//when
		Purchase actual = repository.save(purchase);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void update_validData_shouldUpdatePurchase() {
		//given
		Purchase purchase = purchases.get(0);
		Purchase expected = Purchase.builder().id(1L).quantity(5).price(100).build();
		Assert.assertEquals(expected.getId(), purchase.getId());
		//when
		Purchase actual = repository.save(Purchase.builder().id(1L).quantity(5).price(100).build());
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void findPurchasesByOrder_validData_shouldReturnAllPurchases() {
		//given
		List<Purchase> expected = new ArrayList<>() {{
			add(purchases.get(0));
		}};
		//when
		List<Purchase> actual = repository.findPurchasesByOrder_id(3L);
		//then
		Assert.assertEquals(expected, actual);
	}

}
