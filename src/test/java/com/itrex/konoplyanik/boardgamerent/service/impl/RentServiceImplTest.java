package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.itrex.konoplyanik.boardgamerent.converters.RentConverter;
import com.itrex.konoplyanik.boardgamerent.dto.RentDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RentSaveDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.BoardGameRepository;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;
import com.itrex.konoplyanik.boardgamerent.service.BaseServiceTest;

@SpringBootTest
public class RentServiceImplTest extends BaseServiceTest {

	@InjectMocks
    private RentServiceImpl rentService;
	@Mock
	private RentRepository rentRepository;
	@Mock
	private PurchaseRepository purchaseRepository;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private BoardGameRepository boardGameRepository;
	
	@Test
	public void findById_validData_shouldReturnPurchase() throws RepositoryException, ServiceException {
        //given
		Rent rent = getRents().get(0);
        RentDTO expected = RentConverter.convertRentToDTO(rent);
        // when
        Mockito.when(rentRepository.findById(rent.getId()))
                .thenReturn(Optional.of(rent));
        RentDTO actual = rentService.findById(rent.getId());
        // then
        Assert.assertEquals(expected, actual);
    }
	
	@Test
	public void add_validData_shouldReturnNewPurchaseDTO() throws RepositoryException, ServiceException {
        //given
		Rent rent = Rent.builder().boardGame(getBoardGames().get(0)).order(getOrders().get(0)).rentFrom(LocalDate.of(2021, 10, 25)).rentTo(LocalDate.of(2021, 10, 28)).price(140).build();
		RentDTO expected = RentConverter.convertRentToDTO(rent);
		RentSaveDTO rentSaveDTO = RentSaveDTO.builder()
				.id(rent.getId())
				.boardGameId(rent.getBoardGame().getId())
				.orderId(rent.getOrder().getId())
				.rentFrom(rent.getRentFrom())
				.rentTo(rent.getRentTo())
				.price(rent.getPrice())
				.build();
        //when
		Mockito.when(orderRepository.findById(rentSaveDTO.getOrderId())).thenReturn(Optional.of(getOrders().get(0)));
		Mockito.when(boardGameRepository.findById(rentSaveDTO.getBoardGameId())).thenReturn(Optional.of(getBoardGames().get(0)));
        Mockito.when(rentRepository.save(rent)).thenReturn(rent);
        RentDTO actual = rentService.add(rentSaveDTO);
        //then
        Assert.assertEquals(expected, actual);
    }
	
	@Test
	public void update_validData_shouldReturnNewPurchaseDTO() throws RepositoryException, ServiceException {
		 //given
		Rent rent = getRents().get(0);
		rent.setRentFrom(LocalDate.of(2021, 10, 24));
		rent.setRentTo(LocalDate.of(2021, 10, 28));
		rent.setPrice(72);
		RentSaveDTO rentSaveDTO = RentSaveDTO.builder()
        		.id(rent.getId())
        		.boardGameId(rent.getBoardGame().getId())
				.orderId(rent.getOrder().getId())
        		.rentFrom(rent.getRentFrom())
        		.rentTo(rent.getRentTo())
        		.price(rent.getPrice())
        		.build();
		RentDTO expected = RentDTO.builder().id(1L).boardGameId(1L).boardGameName("Сквозь века").rentFrom(LocalDate.of(2021, 10, 24)).rentTo(LocalDate.of(2021, 10, 28)).price(72).build();
        //when
        Mockito.when(rentRepository.findById(rentSaveDTO.getId())).thenReturn(Optional.of(getRents().get(0)));
        RentDTO actual = rentService.update(rentSaveDTO);
        // then
        Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void delete_validData_shouldReturnTrue() throws RepositoryException, ServiceException {
		//given
		Rent rent = getRents().get(0);
		//when
		Mockito.when(rentRepository.findById(rent.getId())).thenReturn(Optional.of(rent));
		Mockito.when(rentRepository.findRentsByOrder_id(rent.getOrder().getId())).thenReturn(new ArrayList<>());
		Mockito.when(purchaseRepository.findPurchasesByOrder_id(rent.getOrder().getId())).thenReturn(new ArrayList<>());
        //then
        Assert.assertTrue(rentService.delete(1L));
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
        Mockito.when(rentRepository.findRentsByOrder_id(1L)).thenReturn(rents);
        List<RentDTO> actual = rentService.findRentsByOrder(1L);
        // then
        Assert.assertEquals(expected, actual);
	}
}
