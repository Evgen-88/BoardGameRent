package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.entity.Status;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;

public class PurchaseRepositoryImplTest extends BaseRepositoryTest {
	
	@Autowired
	private PurchaseRepository repository;
	
	private List<Purchase> purchases;

	@Before
	public void fill() {
		purchases = new ArrayList<>() {{
			add(new Purchase(1L, 2L, 3L, 2, 26));
			add(new Purchase(2L, 1L, 2L, 3, 36));
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
		Purchase actual = repository.findById(expected.getId());
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void addAll_validData_shouldAddAllPurchases() {
		//given
		List<Purchase> expected = new ArrayList<>() {{
			add(new Purchase(3L, 3L, 4L, 1, 18));
			add(new Purchase(4L, 1L, 2L, 2, 24));
		}};
		//when
		List<Purchase> purchases = new ArrayList<>() {{
			add(new Purchase(3L, 4L, 1, 18));
			add(new Purchase(1L, 2L, 2, 24));
		}};
		List<Purchase> actual = repository.addAll(purchases);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void add_validData_shouldAddPurchase() {
		//given
		Purchase expected = new Purchase(3L, 3L, 4L, 1, 18);
		Purchase purchase = new Purchase(3L, 4L, 1, 18);
		//when
		Purchase actual = repository.add(purchase);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void update_validData_shouldUpdatePurchase() {
		//given
		Purchase purchase = purchases.get(0);
		Purchase expected = new Purchase(1L, 2L, 3L, 5, 100);
		//when
		purchase.setQuantity(5);
		purchase.setPrice(100);
		Purchase actual = repository.update(purchase);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void delete_validData_shouldDeletePurchase() {
		//given
		Purchase purchase = purchases.get(1);
		//when
		boolean actual = repository.delete(purchase.getId());
		//then
		Assert.assertTrue(actual);
	}
	
	@Test
	public void findPurchasesByOrder_validData_shouldReturnAllPurchases() {
		//given
		List<Purchase> expected = new ArrayList<>() {{
			add(purchases.get(0));
		}};
		//when
		List<Purchase> actual = repository.findPurchasesByOrder(3L);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void deletePurchasesFromOrder_shouldDeletePurchase() {
		//given
		Order order = new Order(3L, 2L, 126, LocalDate.of(2021, 10, 23), Status.confirmed);
		//when
		boolean actual = repository.deletePurchasesFromOrder(order.getId());
		//then
		Assert.assertTrue(actual);
		Assert.assertEquals(new ArrayList<>(), repository.findPurchasesByOrder(order.getId()));
	}

}
