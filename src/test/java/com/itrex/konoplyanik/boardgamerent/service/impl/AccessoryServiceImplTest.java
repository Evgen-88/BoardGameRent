package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.itrex.konoplyanik.boardgamerent.dto.AccessoryDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Accessory;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.AccessoryRepository;
import com.itrex.konoplyanik.boardgamerent.service.BaseServiceTest;

@SpringBootTest
public class AccessoryServiceImplTest extends BaseServiceTest {

	@InjectMocks
    private AccessoryServiceImpl accessoryService;
	@Mock
	private AccessoryRepository accessoryRepository;
	
	@Test
	public void findAll_validData_shouldReturnAccessoryList() throws ServiceException {
		//given
        int expected = 3;
        //when
        Mockito.when(accessoryRepository.findAll()).thenReturn(Arrays.asList(new Accessory(), new Accessory(), new Accessory()));
        List<AccessoryDTO> actual = accessoryService.findAll();
        //then
        Assertions.assertEquals(expected, actual.size());
	}
	
	@Test
	public void findById_validData_shouldReturnAccessory() throws ServiceException {
        //given
		Accessory accessory = Accessory.builder().id(1L).name("Протекторы для карт 65х87").price(12).quantity(24).build();
		AccessoryDTO expected = AccessoryDTO.builder().id(1L).name("Протекторы для карт 65х87").price(12).quantity(24).build();
        // when
        Mockito.when(accessoryRepository.findById(accessory.getId()))
                .thenReturn(Optional.of(accessory));
        AccessoryDTO actual = accessoryService.findById(accessory.getId());
        // then
		Assertions.assertEquals(expected, actual);
		Assertions.assertEquals(expected, actual);
    }
	
	@Test
	public void add_validData_shouldReturnNewAccessoryDTO() throws ServiceException {
        //given
		Accessory accessory = Accessory.builder().id(4L).name("Кубик D6").price(6).quantity(52).build();
		AccessoryDTO expected = AccessoryDTO.builder().id(4L).name("Кубик D6").price(6).quantity(52).build();
        //when
        Mockito.when(accessoryRepository.save(accessory)).thenReturn(accessory);
        AccessoryDTO actual = accessoryService.add(expected);
        //then
		Assertions.assertEquals(expected, actual);
    }
	
	@Test
	public void update_validData_shouldReturnNewAccessoryDTO() throws ServiceException {
		 //given
        Accessory accessory = Accessory.builder().id(1L).name("Name").price(4).quantity(2).build();
		AccessoryDTO expected = AccessoryDTO.builder().id(1L).name("updatedName").price(24).quantity(8).build();
        //when
        Mockito.when(accessoryRepository.findById(accessory.getId())).thenReturn(Optional.of(accessory));
        AccessoryDTO actual = accessoryService.update(expected);
        // then
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void delete_validData_shouldReturnTrue() throws ServiceException {
		//given
		Accessory accessory = getAccessories().get(0);
		accessory.setPurchases(new HashSet<>());
		//when
        Mockito.when(accessoryRepository.findAccessoryById(1L)).thenReturn(Optional.of(accessory));
        //then
		Assertions.assertTrue(accessoryService.delete(1L));
	}
	
}
