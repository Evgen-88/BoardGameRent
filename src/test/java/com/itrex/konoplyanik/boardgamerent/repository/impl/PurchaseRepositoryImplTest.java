package com.itrex.konoplyanik.boardgamerent.repository.impl;

import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PurchaseRepositoryImplTest extends BaseRepositoryTest {
	
	@Autowired
	private PurchaseRepository repository;
	
	private static List<Purchase> purchases;

	@BeforeAll
	public static void fill() {
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
		Assertions.assertEquals(purchases, actual);
	}
	
	@Test
	public void findById_validData_shouldReturnPurchaseById() {
		//given
		Purchase expected = purchases.get(0);
		//when
		Purchase actual = repository.findById(expected.getId()).get();
		//then
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void add_validData_shouldAddPurchase() {
		//given
		Purchase expected = Purchase.builder().id(3L).quantity(1).price(18).build();
		Purchase purchase = Purchase.builder().quantity(1).price(18).build();
		//when
		Purchase actual = repository.save(purchase);
		//then
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void update_validData_shouldUpdatePurchase() {
		//given
		Purchase purchase = purchases.get(0);
		Purchase expected = Purchase.builder().id(1L).quantity(5).price(100).build();
		Assertions.assertEquals(expected.getId(), purchase.getId());
		//when
		Purchase actual = repository.save(Purchase.builder().id(1L).quantity(5).price(100).build());
		//then
		Assertions.assertEquals(expected, actual);
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
		Assertions.assertEquals(expected, actual);
	}

}
