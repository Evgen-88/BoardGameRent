package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
import com.itrex.konoplyanik.boardgamerent.repository.AccessoryRepository;
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
	@Mock
	private AccessoryRepository accessoryRepository;
	
	@Test
	public void findById_validData_shouldReturnPurchase() throws RepositoryException, ServiceException {
        //given
        Purchase purchase = getPurchases().get(0);
        PurchaseDTO expected = PurchaseConverter.convertPurchaseToDTO(purchase);
        // when
        Mockito.when(purchaseRepository.findById(1L))
                .thenReturn(Optional.of(purchase));
        PurchaseDTO actual = purchaseService.findById(1L);
        // then
        Assertions.assertEquals(expected, actual);
    }
	
	@Test
	public void add_validData_shouldReturnNewPurchaseDTO() throws RepositoryException, ServiceException {
        //given
		Purchase purchase = Purchase.builder().accessory(getAccessories().get(0)).order(getOrders().get(0)).quantity(4).price(48).build();
		PurchaseDTO expected = PurchaseConverter.convertPurchaseToDTO(purchase);
		PurchaseSaveDTO purchaseSaveDTO = PurchaseSaveDTO.builder()
				.id(purchase.getId())
				.accessoryId(purchase.getAccessory().getId())
				.orderId(purchase.getOrder().getId())
				.quantity(purchase.getQuantity())
				.price(purchase.getPrice())
				.build();
        //when
		Mockito.when(orderRepository.findById(purchaseSaveDTO.getOrderId())).thenReturn(Optional.of(getOrders().get(0)));
		Mockito.when(accessoryRepository.findById(purchaseSaveDTO.getAccessoryId())).thenReturn(Optional.of(getAccessories().get(0)));
        Mockito.when(purchaseRepository.save(purchase)).thenReturn(purchase);
        PurchaseDTO actual = purchaseService.add(purchaseSaveDTO);
        //then
		Assertions.assertEquals(expected, actual);
    }
	
	@Test
	public void update_validData_shouldReturnNewPurchaseDTO() throws RepositoryException, ServiceException {
		 //given
        Purchase purchase = getPurchases().get(0);
        purchase.setQuantity(3);
        purchase.setPrice(36);
        PurchaseSaveDTO purchaseSaveDTO = PurchaseSaveDTO.builder()
        		.id(purchase.getId())
        		.accessoryId(purchase.getAccessory().getId())
        		.orderId(purchase.getOrder().getId())
        		.quantity(purchase.getQuantity())
        		.price(purchase.getPrice())
        		.build();
		PurchaseDTO expected = PurchaseDTO.builder().id(1L).accessoryId(2L).accessoryName("Протекторы для карт 48х64").quantity(3).price(36).build();
        //when
		Mockito.when(purchaseRepository.findById(purchase.getId())).thenReturn(Optional.of(getPurchases().get(0)));
        PurchaseDTO actual = purchaseService.update(purchaseSaveDTO);
        // then
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void delete_validData_shouldReturnTrue() throws RepositoryException, ServiceException {
		//given
		Purchase purchase = getPurchases().get(0);
		//when
		Mockito.when(purchaseRepository.findById(purchase.getId())).thenReturn(Optional.of(purchase));
		Mockito.when(rentRepository.findRentsByOrder_id(purchase.getOrder().getId())).thenReturn(new ArrayList<>());
		Mockito.when(purchaseRepository.findPurchasesByOrder_id(purchase.getOrder().getId())).thenReturn(new ArrayList<>());
        //then
		Assertions.assertTrue(purchaseService.delete(1L));
	}
	
	@Test
	public void findPurchasesByOrder_validData_shouldReturnListPurchaseDTO() throws RepositoryException, ServiceException {
		//given
        Purchase purchase = getPurchases().get(0);
        List<Purchase> purchases = new ArrayList<>() {{
        	add(purchase);
        }};
        List<PurchaseDTO> expected = new ArrayList<>() {{
        	add(PurchaseConverter.convertPurchaseToDTO(purchase));
        }};
        // when
        Mockito.when(purchaseRepository.findPurchasesByOrder_id(3L)).thenReturn(purchases);
        List<PurchaseDTO> actual = purchaseService.findPurchasesByOrder(3L);
        // then
		Assertions.assertEquals(expected, actual);
	}
}
