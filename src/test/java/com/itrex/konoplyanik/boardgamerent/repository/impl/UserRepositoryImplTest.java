package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.entity.User;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;
import com.itrex.konoplyanik.boardgamerent.repository.UserRepository;

@SpringBootTest
public class UserRepositoryImplTest extends BaseRepositoryTest {
	
	@Autowired
	private UserRepository repository;
	
	private List<User> users;

	@Before
	public void fill() {
		users = new ArrayList<>() {{
			add(User.builder().id(1L).login("ivan").password("ivan").name("Иван").phone(1111111).email("ivan@mail").build());
			add(User.builder().id(2L).login("alex").password("alex").name("Алекс").phone(2222222).email("alex@mail").build());
			add(User.builder().id(3L).login("kir").password("kir").name("Кирилл").phone(3333333).email("kir@mail").build());
			add(User.builder().id(4L).login("piter").password("piter").name("Петр").phone(4444444).email("piter@mail").build());
			add(User.builder().id(5L).login("vova").password("vova").name("Владимир").phone(5555555).email("vova@mail").build());
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
	public void add_validData_shouldAddUser() {
		//given
		User expected = User.builder().id(6L).login("vasya").password("vasya").name("Василий").phone(8888888).email("vasya@mail").build();
		User user = User.builder().login("vasya").password("vasya").name("Василий").phone(8888888).email("vasya@mail").build();
		List<Long> roleIds = new ArrayList<>() {{
			add(3L);
		}};
		User actual = repository.add(user, roleIds);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void update_validData_shouldUpdateUser() {
		//given
		User user = users.get(0);
		User expected = User.builder().id(1L).login("1ivan").password("1ivan").name("Иван").phone(1111112).email("1ivan@mail").build();
		Assert.assertEquals(expected.getId(), user.getId());
		//when
		User actual = repository.update(expected);
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
			add(User.builder().id(4L).login("piter").password("piter").name("Петр").phone(4444444).email("piter@mail").build());
			add(User.builder().id(3L).login("kir").password("kir").name("Кирилл").phone(3333333).email("kir@mail").build());
			add(User.builder().id(5L).login("vova").password("vova").name("Владимир").phone(5555555).email("vova@mail").build());
			add(User.builder().id(2L).login("alex").password("alex").name("Алекс").phone(2222222).email("alex@mail").build());
			add(User.builder().id(1L).login("ivan").password("ivan").name("Иван").phone(1111111).email("ivan@mail").build());
		}};
		//when
		List<User>	actual = repository.findUsersByRole(3L);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void deleteRoleFromUser_validData_shouldDeleteRoleFromUser() {
		// given & when
		boolean actual = repository.deleteRoleFromUser(1L, 1L);
		// then
		Assert.assertTrue(actual);
	}
	
	@Test
	public void addRoleToUser_validData_shouldAddRoleToUser() {
		// given
		Long roleId = 2L;
		Role expected = Role.builder().id(2L).name("manager").build();
		// when
		Role	actual = repository.addRoleToUser(1L, roleId);
		// then
		Assert.assertEquals(expected, actual);
	}
	
}
