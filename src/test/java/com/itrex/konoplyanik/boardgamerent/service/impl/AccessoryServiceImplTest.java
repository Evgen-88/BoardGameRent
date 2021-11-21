package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.itrex.konoplyanik.boardgamerent.dto.AccessoryDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Accessory;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
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
	public void findAll_validData_shouldReturnAccessoryList() throws RepositoryException, ServiceException {
		//given
        int expected = 3;
        //when
        Mockito.when(accessoryRepository.findAll()).thenReturn(Arrays.asList(new Accessory(), new Accessory(), new Accessory()));
        List<AccessoryDTO> actual = accessoryService.findAll();
        //then
        Assert.assertEquals(expected, actual.size());
	}
	
	@Test
	public void findById_validData_shouldReturnAccessory() throws RepositoryException, ServiceException {
        //given
        String expected = "Протекторы для карт 65х87";
        // when
        Mockito.when(accessoryRepository.findById(1L))
                .thenReturn(Accessory.builder().id(1L).name("Протекторы для карт 65х87").price(12).quantity(24).build());
        String actual = accessoryService.findById(1L).getName();
        // then
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actual);
    }
	
	@Test
	public void add_validData_shouldReturnNewAccessoryDTO() throws RepositoryException, ServiceException {
        //given
		Accessory accessory = Accessory.builder().id(4L).name("Кубик D6").price(6).quantity(52).build();
		AccessoryDTO expected = AccessoryDTO.builder().id(4L).name("Кубик D6").price(6).quantity(52).build();
        //when
        Mockito.when(accessoryRepository.add(accessory)).thenReturn(accessory);
        AccessoryDTO actual = accessoryService.add(expected);
        //then
        Assert.assertEquals(expected, actual);
    }
	
	@Test
	public void update_validData_shouldReturnNewAccessoryDTO() throws RepositoryException, ServiceException {
		 //given
        Accessory accessory = Accessory.builder().id(1L).name("updatedName").price(24).quantity(8).build();
		AccessoryDTO expected = AccessoryDTO.builder().id(1L).name("updatedName").price(24).quantity(8).build();
        //when
        Mockito.when(accessoryRepository.update(accessory)).thenReturn(accessory);
        AccessoryDTO actual = accessoryService.update(expected);
        // then
        Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void delete_validData_shouldReturnTrue() throws RepositoryException, ServiceException {
		//given && when
        Mockito.when(accessoryRepository.delete(1L)).thenReturn(true);
        //then
        Assert.assertTrue(accessoryService.delete(1L));
	}
	
}
