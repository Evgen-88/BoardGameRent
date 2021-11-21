package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;
import com.itrex.konoplyanik.boardgamerent.repository.RoleRepository;

@SpringBootTest
public class RoleRepositoryImplTest extends BaseRepositoryTest {
	
	@Autowired
	private RoleRepository repository;
	
	private List<Role> roles;

	@Before
	public void fill() {
		roles = new ArrayList<>() {{
			add(Role.builder().id(1L).name("admin").build());
			add(Role.builder().id(2L).name("manager").build());
			add(Role.builder().id(3L).name("customer").build());
		}};
	}
	
	@Test
	public void findAll_validData_shouldReturnAllRoles() {
		//given && when
		List<Role>	actual = repository.findAll();
		//then
		Assert.assertEquals(roles, actual);
	}
	
	@Test
	public void findById_validData_shouldReturnRoleById() {
		//given
		Role expected = roles.get(0);
		//when
		Role actual = repository.findById(expected.getId());
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void add_validData_shouldAddRole() {
		//given
		Role expected = Role.builder().id(4L).name("secretary").build();
		Role role = Role.builder().name("secretary").build();
		//when
		Role actual = repository.add(role);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void update_validData_shouldUpdateRole() {
		//given
		Role role = roles.get(0);
		Role expected = Role.builder().id(1L).name("boss").build();
		Assert.assertEquals(expected.getId(), role.getId());
		//when
		Role actual = repository.update(expected);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void delete_validData_shouldDeleteRole() {
		//given
		Role role = roles.get(1);
		//when
		boolean actual = repository.delete(role.getId());
		//then
		Assert.assertTrue(actual);
	}
	
	@Test
	public void findRolesByUser_validData_shouldReturnAllRolesByUser() {
		//given
		List<Role>	expected = new ArrayList<>() {{
			add(Role.builder().id(1L).name("admin").build());
			add(Role.builder().id(3L).name("customer").build());
		}};
		//when
		List<Role>	actual = repository.findRolesByUser(1L);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void deleteRolesFromUser_validData_shouldDeleteRolesFromUser() {
		// given
		List<Long> roleIds = new ArrayList<>() {{
			add(1L);
			add(3L);
		}};
		// when
		boolean actual = repository.deleteRolesFromUser(1L, roleIds);
		// then
		Assert.assertTrue(actual);
	}
	
	@Test
	public void addRolesToUser_validData_shouldAddRolesToUser() {
		// given
		List<Long> roleIds = new ArrayList<>() {{
			add(2L);
		}};
		List<Role> expected = new ArrayList<>() {{
			add(Role.builder().id(2L).name("manager").build());
		}};
		// when
		List<Role>	actual = repository.addRolesToUser(1L, roleIds);
		// then
		Assert.assertEquals(expected, actual);
	}

}
