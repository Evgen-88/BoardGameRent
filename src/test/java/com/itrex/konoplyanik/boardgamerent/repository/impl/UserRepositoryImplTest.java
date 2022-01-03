package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itrex.konoplyanik.boardgamerent.entity.User;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;
import com.itrex.konoplyanik.boardgamerent.repository.UserRepository;

@SpringBootTest
public class UserRepositoryImplTest extends BaseRepositoryTest {
	
	@Autowired
	private UserRepository repository;
	
	private static List<User> users;

	@BeforeAll
	public static void fill() {
		users = new ArrayList<>() {{
			add(User.builder().id(1L).login("ivan").password("$2a$12$Zz1osbGrGZrFpB0OuozlZuqtcjKdr0iAN0OM0/BdVlZhPxaMx01jW").name("Иван").phone(1111111).email("ivan@mail").build());
			add(User.builder().id(2L).login("alex").password("$2a$12$QmvrBgy8.RAuCj9cZ3yQDek1ZOKY/nRF7vb7kyIOWo7M9aWxsngi6").name("Алекс").phone(2222222).email("alex@mail").build());
			add(User.builder().id(3L).login("kir").password("$2a$12$yaZL8PGRDB4MiwrwPajavOEWgL5d1FyldWWWEMWSzm2v/xqzHuU0C").name("Кирилл").phone(3333333).email("kir@mail").build());
			add(User.builder().id(4L).login("piter").password("$2a$12$AhLOgtPWDytNQtixRXef8.KLeZNPUDNgzjJ/VB/yZJnivQ0rSSkFC").name("Петр").phone(4444444).email("piter@mail").build());
			add(User.builder().id(5L).login("vova").password("$2a$12$eIV9PyAhpi9SvGTG.c/DU.CuP54stjgX/uiMNMQHCeqre/2/6mPZm").name("Владимир").phone(5555555).email("vova@mail").build());
		}};
	}
	
	@Test
	public void findAll_validData_shouldReturnAllUsers() {
		//given && when
		List<User>	actual = repository.findAll();
		//then
		Assertions.assertEquals(users, actual);
	}
	
	@Test
	public void findById_validData_shouldReturnUserById() {
		//given
		User expected = users.get(0);
		//when
		User actual = repository.findById(expected.getId()).get();
		//then
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void add_validData_shouldAddUser() {
		//given
		User expected = User.builder().id(6L).login("vasya").password("vasya").name("Василий").phone(8888888).email("vasya@mail").build();
		User user = User.builder().login("vasya").password("vasya").name("Василий").phone(8888888).email("vasya@mail").build();
		User actual = repository.save(user);
		//then
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void update_validData_shouldUpdateUser() {
		//given
		User user = users.get(0);
		User expected = User.builder().id(1L).login("1ivan").password("1ivan").name("Иван").phone(1111112).email("1ivan@mail").build();
		Assertions.assertEquals(expected.getId(), user.getId());
		//when
		User actual = repository.save(expected);
		//then
		Assertions.assertEquals(expected, actual);
	}
	
}
