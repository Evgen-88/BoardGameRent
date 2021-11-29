package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.itrex.konoplyanik.boardgamerent.dto.BoardGameDTO;
import com.itrex.konoplyanik.boardgamerent.entity.BoardGame;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.BoardGameRepository;
import com.itrex.konoplyanik.boardgamerent.service.BaseServiceTest;

@SpringBootTest
public class BoardGameServiceImplTest extends BaseServiceTest {

	@InjectMocks
    private BoardGameServiceImpl boardGameService;
	@Mock
	private BoardGameRepository boardGameRepository;
	
	@Test
	public void findAll_validData_shouldReturnBoardGameList() throws RepositoryException, ServiceException {
		//given
        int expected = 4;
        //when
        Mockito.when(boardGameRepository.findAll()).thenReturn(Arrays.asList(new BoardGame(), new BoardGame(), new BoardGame(), new BoardGame()));
        List<BoardGameDTO> actual = boardGameService.findAll();
        //then
        Assert.assertEquals(expected, actual.size());
	}
	
	@Test
	public void findById_validData_shouldReturnBoardGame() throws RepositoryException, ServiceException {
        //given
        String expected = "Сквозь века";
        // when
        Mockito.when(boardGameRepository.findById(1L))
                .thenReturn(Optional.of(BoardGame.builder().id(1L).name("Сквозь века").rentPrice(45).quantity(3).build()));
        String actual = boardGameService.findById(1L).getName();
        // then
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actual);
    }
	
	@Test
	public void add_validData_shouldReturnNewAccessoryDTO() throws RepositoryException, ServiceException {
        //given
		BoardGame boardGame = BoardGame.builder().id(5L).name("Замки Бургундии").rentPrice(60).quantity(2).build();
		BoardGameDTO expected = BoardGameDTO.builder().id(5L).name("Замки Бургундии").rentPrice(60).quantity(2).build();
        //when
        Mockito.when(boardGameRepository.save(boardGame)).thenReturn(boardGame);
        BoardGameDTO actual = boardGameService.add(expected);
        //then
        Assert.assertEquals(expected, actual);
    }
	
	@Test
	public void update_validData_shouldReturnNewAccessoryDTO() throws RepositoryException, ServiceException {
		 //given
		BoardGame boardGame = BoardGame.builder().id(1L).name("updatedName").rentPrice(45).quantity(3).build();
        BoardGameDTO expected = BoardGameDTO.builder().id(1L).name("updatedName").rentPrice(45).quantity(3).build();
        //when
        Mockito.when(boardGameRepository.findById(boardGame.getId())).thenReturn(Optional.of(boardGame));
        BoardGameDTO actual = boardGameService.update(expected);
        // then
        Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void delete_validData_shouldReturnTrue() throws RepositoryException, ServiceException {
		//given
		BoardGame boardGame = BoardGame.builder().id(1L).rents(new HashSet<>()).name("updatedName").rentPrice(45).quantity(3).build();
		//when
        Mockito.when(boardGameRepository.findBoardGameById(boardGame.getId())).thenReturn(Optional.of(boardGame));
        //then
        Assert.assertTrue(boardGameService.delete(1L));
	}
	
}
