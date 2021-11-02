package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.itrex.konoplyanik.boardgamerent.entity.BoardGame;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;
import com.itrex.konoplyanik.boardgamerent.repository.BoardGameRepository;

public class BoardGameRepositoryImplTest extends BaseRepositoryTest {
	private final BoardGameRepository repository;
	private List<BoardGame> boardGames;

	public BoardGameRepositoryImplTest() {
		super();
		repository = getApplicationContext().getBean(BoardGameRepositoryImpl.class);
	}
	
	@Before
	public void fill() {
		boardGames = new ArrayList<>() {{
			add(new BoardGame(1L, "Сквозь века", 45, 3));
			add(new BoardGame(2L, "Сумеречная борьба", 40, 2));
			add(new BoardGame(3L, "Звездные Войны. Восстание", 50, 2));
			add(new BoardGame(4L, "Особняки безумия", 50, 4));
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
	public void addAll_validData_shouldAddAllBoardGames() {
		//given
		List<BoardGame> expected = new ArrayList<>() {{
			add(new BoardGame(5L, "Сезоны", 35, 4));
			add(new BoardGame(6L, "Остров духов", 45, 3));
		}};
		//when
		List<BoardGame> boardGames = new ArrayList<>() {{
			add(new BoardGame("Сезоны", 35, 4));
			add(new BoardGame("Остров духов", 45, 3));
		}};
		List<BoardGame> actual = repository.addAll(boardGames);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void add_validData_shouldAddBoardGame() {
		//given
		BoardGame expected = new BoardGame(5L, "Замки Бургундии", 60, 2);
		BoardGame boardGame = new BoardGame("Замки Бургундии", 60, 2);
		//when
		BoardGame actual = repository.add(boardGame);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void update_validData_shouldUpdateBoardGame() {
		//given
		BoardGame boardGame = boardGames.get(0);
		BoardGame expected = new BoardGame(1L, "Пандемия", 30, 5);
		//when
		boardGame.setName("Пандемия");
		boardGame.setRentPrice(30);
		boardGame.setQuantity(5);
		BoardGame actual = repository.update(boardGame);
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
