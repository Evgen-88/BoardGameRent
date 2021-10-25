package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.itrex.konoplyanik.boardgamerent.entity.User;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;
import com.itrex.konoplyanik.boardgamerent.repository.UserRepository;

public class JDBCUserRepositoryImplTest extends BaseRepositoryTest {
	private final UserRepository repository;
	private List<User> users;
	
	public JDBCUserRepositoryImplTest() {
		super();
		repository = new JDBCUserRepositoryImpl(getConnectionPool());
		users = new ArrayList<>() {{
			add(new User(1L, "ivan", "ivan", "Иван", 1111111, "ivan@mail"));
			add(new User(2L, "alex", "alex", "Алекс", 2222222, "alex@mail"));
			add(new User(3L, "kir", "kir", "Кирилл", 3333333, "kir@mail"));
			add(new User(4L, "piter", "piter", "Петр", 4444444, "piter@mail"));
			add(new User(5L, "vova", "vova", "Владимир", 5555555, "vova@mail"));
		}};
	}
	
	@Test
	public void findAll_validData_shouldReturnAllUsers() {
		//given && when
		List<User>	actual = repository.findAll();
		//then
		Assert.assertEquals(users, actual);
	}
	
	@Test
	public void findById_validData_shouldReturnUserById() {
		//given
		User expected = users.get(0);
		//when
		User actual = repository.findById(expected.getId());
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void addAll_validData_shouldAddAllUsers() {
		//given
		List<User> expected = new ArrayList<>() {{
			add(new User(6L, "dima", "dima", "Дмитрий", 6666666, "dima@mail"));
			add(new User(7L, "serg", "serg", "Сергей", 7777777, "serg@mail"));
		}};
		//when
		List<User> users = new ArrayList<>() {{
			add(new User("dima", "dima", "Дмитрий", 6666666, "dima@mail"));
			add(new User("serg", "serg", "Сергей", 7777777, "serg@mail"));
		}};
		List<User> actual = repository.addAll(users);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void add_validData_shouldAddUser() {
		//given
		User expected = new User(6L, "vasya", "vasya", "Василий", 8888888, "vasya@mail");
		User user = new User("vasya", "vasya", "Василий", 8888888, "vasya@mail");
		//when
		User actual = repository.add(user);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void update_validData_shouldUpdateUser() {
		//given
		User user = users.get(0);
		User expected = new User(1L, "1ivan", "1ivan", "Ваня", 1111112, "1ivan@mail");
		//when
		user.setLogin("1ivan");
		user.setPassword("1ivan");
		user.setName("Ваня");
		user.setPhone(1111112);
		user.setEmail("1ivan@mail");
		User actual = repository.update(user);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void delete_validData_shouldDeleteUser() {
		//given
		User user = users.get(1);
		//when
		boolean actual = repository.delete(user.getId());
		//then
		Assert.assertTrue(actual);
	}
	
	@Test
	public void findUsersByRole_validData_shouldReturnAllUsersByRole() {
		//given
		List<User>	expected = new ArrayList<>() {{
			add(new User(1L, "ivan", "ivan", "Иван", 1111111, "ivan@mail"));
			add(new User(2L, "alex", "alex", "Алекс", 2222222, "alex@mail"));
			add(new User(3L, "kir", "kir", "Кирилл", 3333333, "kir@mail"));
			add(new User(4L, "piter", "piter", "Петр", 4444444, "piter@mail"));
			add(new User(5L, "vova", "vova", "Владимир", 5555555, "vova@mail"));
		}};
		//when
		List<User>	actual = repository.findUsersByRole(3L);
		//then
		Assert.assertEquals(expected, actual);
	}
	
}
