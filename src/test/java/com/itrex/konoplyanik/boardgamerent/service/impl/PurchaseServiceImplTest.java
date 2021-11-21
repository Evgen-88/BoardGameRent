package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.itrex.konoplyanik.boardgamerent.converters.PurchaseConverter;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseSaveDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;
import com.itrex.konoplyanik.boardgamerent.service.BaseServiceTest;

@SpringBootTest
public class PurchaseServiceImplTest extends BaseServiceTest{

	@InjectMocks
    private PurchaseServiceImpl purchaseService;
	@Mock
	private PurchaseRepository purchaseRepository;
	@Mock
	private RentRepository rentRepository;
	@Mock
	private OrderRepository orderRepository;
	
	@Test
	public void findById_validData_shouldReturnPurchase() throws RepositoryException, ServiceException {
        //given
        Purchase purchase = getPurchases().get(0);
        PurchaseDTO expected = PurchaseConverter.convertPurchaseToDTO(purchase);
        // when
        Mockito.when(purchaseRepository.findById(1L))
                .thenReturn(purchase);
        PurchaseDTO actual = purchaseService.findById(1L);
        // then
        Assert.assertEquals(expected, actual);
    }
	
	@Test
	public void add_validData_shouldReturnNewPurchaseDTO() throws RepositoryException, ServiceException {
        //given
		Purchase purchase = Purchase.builder().accessory(getAccessories().get(0)).order(getOrders().get(0)).quantity(4).price(48).build();
		PurchaseDTO expected = PurchaseConverter.convertPurchaseToDTO(purchase);
		PurchaseSaveDTO purchaseSaveDTO = PurchaseSaveDTO.builder()
				.id(purchase.getId())
				.accessory(purchase.getAccessory())
				.order(purchase.getOrder())
				.quantity(purchase.getQuantity())
				.price(purchase.getPrice())
				.build();
        //when
        Mockito.when(purchaseRepository.add(purchase)).thenReturn(purchase);
        PurchaseDTO actual = purchaseService.add(purchaseSaveDTO);
        //then
        Assert.assertEquals(expected, actual);
    }
	
	@Test
	public void update_validData_shouldReturnNewPurchaseDTO() throws RepositoryException, ServiceException {
		 //given
        Purchase purchase = getPurchases().get(0);
        purchase.setQuantity(3);
        purchase.setPrice(36);
        PurchaseSaveDTO purchaseSaveDTO = PurchaseSaveDTO.builder()
        		.id(purchase.getId())
        		.accessory(purchase.getAccessory())
        		.order(purchase.getOrder())
        		.quantity(purchase.getQuantity())
        		.price(purchase.getPrice())
        		.build();
		PurchaseDTO expected = PurchaseDTO.builder().id(1L).accessoryId(2L).accessoryName("Протекторы для карт 48х64").quantity(3).price(36).build();
        //when
        Mockito.when(purchaseRepository.update(purchase)).thenReturn(purchase);
        PurchaseDTO actual = purchaseService.update(purchaseSaveDTO);
        // then
        Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void delete_validData_shouldReturnTrue() throws RepositoryException, ServiceException {
		//given
		Purchase purchase = getPurchases().get(0);
		//when
		Mockito.when(purchaseRepository.delete(purchase.getId())).thenReturn(true);
		Mockito.when(purchaseRepository.findById(purchase.getId())).thenReturn(purchase);
		Mockito.when(rentRepository.findRentsByOrder(purchase.getOrder().getId())).thenReturn(new ArrayList<>());
		Mockito.when(purchaseRepository.findPurchasesByOrder(purchase.getOrder().getId())).thenReturn(new ArrayList<>());
        //then
        Assert.assertTrue(purchaseService.delete(1L));
	}
}
