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
import org.springframework.boot.test.context.SpringBootTest;

import com.itrex.konoplyanik.boardgamerent.converters.OrderConverter;
import com.itrex.konoplyanik.boardgamerent.converters.UserConverter;
import com.itrex.konoplyanik.boardgamerent.dto.OrderDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListForUserDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderSaveDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.entity.Status;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.service.BaseServiceTest;

@SpringBootTest
public class OrderServiceImplTest extends BaseServiceTest {

	@InjectMocks
    private OrderServiceImpl orderService;
	@Mock
	private OrderRepository orderRepository;
	
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
        order.setPurchases(purchases);
        order.setRents(rents);
        OrderDTO expected = OrderConverter.convertOrderToDTO(order);
        // when
        Mockito.when(orderRepository.findById(2L)).thenReturn(order);
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
		OrderSaveDTO expected = OrderConverter.convertOrderToSaveDTO(order);
		OrderSaveDTO orderSaveDTO = OrderSaveDTO.builder().id(5L)
				.id(order.getId())
				.userId(order.getUser().getId())
				.totalPrice(order.getTotalPrice())
				.date(order.getDate())
				.status(order.getStatus())
				.build();
        //when
        Mockito.when(orderRepository.add(order)).thenReturn(order);
        OrderSaveDTO actual = orderService.add(orderSaveDTO);
        //then
        Assert.assertEquals(expected, actual);
    }
	
	@Test
	public void update_validData_shouldReturnNewOrderDTO() throws RepositoryException, ServiceException {
		//given
		Order order = Order.builder().id(2L).user(getUsers().get(3)).date(LocalDate.of(2021, 10, 30)).totalPrice(400).status(Status.booked).build();
		OrderSaveDTO expected = OrderConverter.convertOrderToSaveDTO(order);
		OrderSaveDTO orderSaveDTO = OrderSaveDTO.builder().id(5L)
				.id(order.getId())
				.userId(order.getUser().getId())
				.totalPrice(order.getTotalPrice())
				.date(order.getDate())
				.status(order.getStatus())
				.build();
        //when
        Mockito.when(orderRepository.update(order)).thenReturn(order);
        OrderSaveDTO actual = orderService.update(orderSaveDTO);
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
	public void findOrdersByUser_validData_shouldReturnListOrderDTO() throws RepositoryException, ServiceException {
		//given
		Order order = getOrders().get(0);
        List<Order> orders = new ArrayList<>() {{
        	add(order);
        }};
        List<OrderListDTO> expected = new ArrayList<>() {{
        	add(OrderListDTO.builder()
        			.id(order.getId())
        			.totalPrice(order.getTotalPrice())
        			.date(order.getDate())
        			.status(order.getStatus())
        			.user(UserConverter.convertUserToBaseDTO(order.getUser()))
        			.build());
        }};
        // when
        Mockito.when(orderRepository.findOrdersByUser(1L)).thenReturn(orders);
        List<OrderListDTO> actual = orderService.findOrdersByUser(1L);
        // then
        Assert.assertEquals(expected, actual);
	}

	@Test
	public void findOrderListForUserByUser_validData_shouldReturnOrderListForUserDTO() throws RepositoryException, ServiceException {
		//given
		Order order = getOrders().get(0);
		List<Order> orders = new ArrayList<>() {{
        	add(order);
        }};
        List<OrderListForUserDTO> expected = new ArrayList<>() {{
        	add(OrderListForUserDTO.builder()
        			.id(order.getId())
		        	.totalPrice(order.getTotalPrice())
		        	.date(order.getDate())
		        	.status(order.getStatus())
		        	.build());
        }};
		// when
		Mockito.when(orderRepository.findOrdersByUser(1L)).thenReturn(orders);
		List<OrderListForUserDTO> actual = orderService.findOrderListForUserByUser(1L);
		// then
		Assert.assertEquals(expected, actual);
	}
	
}
