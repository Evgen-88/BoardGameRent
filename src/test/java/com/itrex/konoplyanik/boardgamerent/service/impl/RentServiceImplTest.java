package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;

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
	
	@Test
	public void findById_validData_shouldReturnPurchase() throws RepositoryException, ServiceException {
        //given
		Rent rent = getRents().get(0);
        RentDTO expected = RentConverter.convertRentToDTO(rent);
        // when
        Mockito.when(rentRepository.findById(rent.getId()))
                .thenReturn(rent);
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
				.boardGame(rent.getBoardGame())
				.order(rent.getOrder())
				.rentFrom(rent.getRentFrom())
				.rentTo(rent.getRentTo())
				.price(rent.getPrice())
				.build();
        //when
        Mockito.when(rentRepository.add(rent)).thenReturn(rent);
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
        		.boardGame(rent.getBoardGame())
        		.order(rent.getOrder())
        		.rentFrom(rent.getRentFrom())
        		.rentTo(rent.getRentTo())
        		.price(rent.getPrice())
        		.build();
		RentDTO expected = RentDTO.builder().id(1L).boardGameId(1L).boardGameName("Сквозь века").rentFrom(LocalDate.of(2021, 10, 24)).rentTo(LocalDate.of(2021, 10, 28)).price(72).build();
        //when
        Mockito.when(rentRepository.update(rent)).thenReturn(rent);
        RentDTO actual = rentService.update(rentSaveDTO);
        // then
        Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void delete_validData_shouldReturnTrue() throws RepositoryException, ServiceException {
		//given
		Rent rent = getRents().get(0);
		//when
		Mockito.when(rentRepository.delete(1L)).thenReturn(true);
		Mockito.when(rentRepository.findById(rent.getId())).thenReturn(rent);
		Mockito.when(rentRepository.findRentsByOrder(rent.getOrder().getId())).thenReturn(new ArrayList<>());
		Mockito.when(purchaseRepository.findPurchasesByOrder(rent.getOrder().getId())).thenReturn(new ArrayList<>());
        //then
        Assert.assertTrue(rentService.delete(1L));
	}
}
