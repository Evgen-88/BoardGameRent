package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import com.itrex.konoplyanik.boardgamerent.converters.OrderConverter;
import com.itrex.konoplyanik.boardgamerent.converters.PurchaseConverter;
import com.itrex.konoplyanik.boardgamerent.converters.RentConverter;
import com.itrex.konoplyanik.boardgamerent.dto.OrderDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderSaveDTO;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RentDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.entity.Status;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;
import com.itrex.konoplyanik.boardgamerent.service.BaseServiceTest;

public class OrderServiceImplTest extends BaseServiceTest {

	@InjectMocks
    private OrderServiceImpl orderService;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private PurchaseRepository purchaseRepository;
	@Mock
	private RentRepository rentRepository;
	
	@Test
	public void findAll_validData_shouldReturnOrderList() throws RepositoryException, ServiceException {
		 //given
        List<Order> orders = getOrders();
        List<OrderListDTO> expected = getOrders().stream().map(order -> OrderConverter.convertOrderToListDTO(order)).collect(Collectors.toList());
        // when
        Mockito.when(orderRepository.findAll())
                .thenReturn(orders);
        List<OrderListDTO> actual = orderService.findAll();
        // then
        Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void findById_validData_shouldReturnOrderDTO() throws RepositoryException, ServiceException {
		 //given
        Order order = getOrders().get(1);
        Set<Purchase> purchases = new HashSet<>() {{
        	add(getPurchases().get(1));
        }};
        Set<Rent> rents = new HashSet<>() {{
        	add(getRents().get(1));
        }};
        OrderDTO expected = OrderConverter.convertOrderToDTO(getOrders().get(1));
        expected.setPurchases(PurchaseConverter.convertSetPurchasesToDTO(purchases));
        expected.setRents(RentConverter.convertSetRentsToDTO(rents));
        // when
        Mockito.when(orderRepository.findById(2L)).thenReturn(order);
        Mockito.when(purchaseRepository.findPurchasesByOrder(2L)).thenReturn(new ArrayList<>(purchases));
        Mockito.when(rentRepository.findRentsByOrder(2L)).thenReturn(new ArrayList<>(rents));
        OrderDTO actual = orderService.findById(2L);
        // then
        Assert.assertEquals(expected, actual);
    }
	
	@Test
	public void add_validData_shouldReturnNewOrderDTO() throws RepositoryException, ServiceException {
        //given
		Set<Purchase> purchases = new HashSet<>() {{
			add(Purchase.builder().accessory(getAccessories().get(0)).order(getOrders().get(0)).quantity(4).price(48).build());
		}};
		Set<Rent> rents = new HashSet<>() {{
			add(Rent.builder().boardGame(getBoardGames().get(0)).order(getOrders().get(0)).rentFrom(LocalDate.of(2021, 10, 25)).rentTo(LocalDate.of(2021, 10, 28)).price(140).build());
		}};
		Order order = Order.builder().id(5L).user(getUsers().get(0)).purchases(purchases).rents(rents).date(LocalDate.of(2021, 10, 25)).totalPrice(188).status(Status.confirmed).build();
		OrderDTO expected = OrderConverter.convertOrderToDTO(order);
		OrderSaveDTO orderSaveDTO = OrderSaveDTO.builder().id(5L)
				.id(order.getId())
				.user(order.getUser())
				.totalPrice(order.getTotalPrice())
				.date(order.getDate())
				.status(order.getStatus())
				.build();
        //when
        Mockito.when(orderRepository.add(order)).thenReturn(order);
        OrderDTO actual = orderService.add(orderSaveDTO);
        //then
        Assert.assertEquals(expected, actual);
    }
	
	@Test
	public void update_validData_shouldReturnNewOrderDTO() throws RepositoryException, ServiceException {
		//given
		Order order = Order.builder().id(2L).user(getUsers().get(3)).date(LocalDate.of(2021, 10, 30)).totalPrice(400).status(Status.booked).build();
		OrderDTO expected = OrderConverter.convertOrderToDTO(order);
		OrderSaveDTO orderSaveDTO = OrderSaveDTO.builder().id(5L)
				.id(order.getId())
				.user(order.getUser())
				.totalPrice(order.getTotalPrice())
				.date(order.getDate())
				.status(order.getStatus())
				.build();
        //when
        Mockito.when(orderRepository.update(order)).thenReturn(order);
        OrderDTO actual = orderService.update(orderSaveDTO);
        // then
        Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void delete_validData_shouldReturnTrue() throws RepositoryException, ServiceException {
		//given && when
        Mockito.when(orderRepository.delete(1L)).thenReturn(true);
        //then
        Assert.assertTrue(orderService.delete(1L));
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
        Mockito.when(purchaseRepository.findPurchasesByOrder(3L)).thenReturn(purchases);
        List<PurchaseDTO> actual = orderService.findPurchasesByOrder(3L);
        // then
        Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void findRentsByOrder_validData_shouldReturnListRentDTO() throws RepositoryException, ServiceException {
		//given
		Rent rent = getRents().get(0);
		List<Rent> rents = new ArrayList<>() {{
        	add(rent);
        }};
        List<RentDTO> expected = new ArrayList<>() {{
        	add(RentConverter.convertRentToDTO(rent));
        }};
        // when
        Mockito.when(rentRepository.findRentsByOrder(1L)).thenReturn(rents);
        List<RentDTO> actual = orderService.findRentsByOrder(1L);
        // then
        Assert.assertEquals(expected, actual);
	}
	
}
