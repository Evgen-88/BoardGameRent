package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.itrex.konoplyanik.boardgamerent.entity.BoardGame;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;
import com.itrex.konoplyanik.boardgamerent.repository.BoardGameRepository;

public class BoardGameRepositoryImplTest extends BaseRepositoryTest {
	
	@Autowired
	private BoardGameRepository repository;
	
	private List<BoardGame> boardGames;

	@Before
	public void fill() {
		boardGames = new ArrayList<>() {{
			add(BoardGame.builder().id(1L).name("Сквозь века").rentPrice(45).quantity(3).build());
			add(BoardGame.builder().id(2L).name("Сумеречная борьба").rentPrice(40).quantity(2).build());
			add(BoardGame.builder().id(3L).name("Звездные Войны. Восстание").rentPrice(50).quantity(2).build());
			add(BoardGame.builder().id(4L).name("Особняки безумия").rentPrice(50).quantity(4).build());
		}};
	}

	@Test
	public void findAll_validData_shouldReturnAllBoardGames() {
		//given && when
		List<BoardGame>	actual = repository.findAll();
		//then
		Assert.assertEquals(boardGames, actual);
	}
	
	@Test
	public void findById_validData_shouldReturnBoardGameById() {
		//given
		BoardGame expected = boardGames.get(0);
		//when
		BoardGame actual = repository.findById(expected.getId());
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void add_validData_shouldAddBoardGame() {
		//given
		BoardGame expected = BoardGame.builder().id(5L).name("Сезоны").rentPrice(35).quantity(4).build();
		BoardGame boardGame = BoardGame.builder().name("Сезоны").rentPrice(35).quantity(4).build();
		//when
		BoardGame actual = repository.add(boardGame);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void update_validData_shouldUpdateBoardGame() {
		//given
		BoardGame boardGame = boardGames.get(0);
		BoardGame expected = BoardGame.builder().id(1L).name("Пандемия").rentPrice(30).quantity(5).build();
		Assert.assertEquals(boardGame.getId(), expected.getId());
		//when
		BoardGame actual = repository.update(BoardGame.builder().id(1L).name("Пандемия").rentPrice(30).quantity(5).build());
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void delete_validData_shouldDeleteBoardGame() {
		//given
		BoardGame boardGame = boardGames.get(1);
		//when
		boolean actual = repository.delete(boardGame.getId());
		//then
		Assert.assertTrue(actual);
	}
}
