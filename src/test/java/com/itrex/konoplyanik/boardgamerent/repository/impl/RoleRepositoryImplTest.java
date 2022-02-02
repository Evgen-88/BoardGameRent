package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;
import com.itrex.konoplyanik.boardgamerent.repository.RoleRepository;

public class RoleRepositoryImplTest extends BaseRepositoryTest {

	@Autowired
	private RoleRepository repository;

	private static List<Role> roles;

	@BeforeAll
	public static void fill() {
		roles = new ArrayList<>() {{
			add(Role.builder().id(1L).name("admin").build());
			add(Role.builder().id(2L).name("manager").build());
			add(Role.builder().id(3L).name("customer").build());
		}};
	}

	@Test
	public void findAll_validData_shouldReturnAllRoles() {
		// given && when
		List<Role> actual = repository.findAll();
		// then
		Assertions.assertEquals(roles, actual);
	}

	@Test
	public void findById_validData_shouldReturnRoleById() {
		// given
		Role expected = roles.get(0);
		// when
		Role actual = repository.findById(expected.getId()).get();
		// then
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void add_validData_shouldAddRole() {
		// given
		Role expected = Role.builder().id(4L).name("secretary").build();
		Role role = Role.builder().name("secretary").build();
		// when
		Role actual = repository.save(role);
		// then
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void update_validData_shouldUpdateRole() {
		// given
		Role role = roles.get(0);
		Role expected = Role.builder().id(1L).name("boss").build();
		Assertions.assertEquals(expected.getId(), role.getId());
		// when
		Role actual = repository.save(expected);
		// then
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void findRolesByUser_validData_shouldReturnAllRolesByUser() {
		// given
		List<Role> expected = new ArrayList<>() {{
			add(Role.builder().id(1L).name("admin").build());
			add(Role.builder().id(3L).name("customer").build());
		}};
		// when
		List<Role> actual = repository.findRolesByUsers_id(1L);
		// then
		Assertions.assertEquals(expected, actual);
	}

}
