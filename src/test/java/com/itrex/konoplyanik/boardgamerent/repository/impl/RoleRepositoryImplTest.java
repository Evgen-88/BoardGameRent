package com.itrex.konoplyanik.boardgamerent.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.repository.BaseRepositoryTest;
import com.itrex.konoplyanik.boardgamerent.repository.RoleRepository;


public class RoleRepositoryImplTest extends BaseRepositoryTest {
	
	@Autowired
	private RoleRepository repository;
	
	private List<Role> roles;

	@Before
	public void fill() {
		roles = new ArrayList<>() {{
			add(new Role(1L, "admin"));
			add(new Role(2L, "manager"));
			add(new Role(3L, "customer"));
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
	public void addAll_validData_shouldAddAllRoles() {
		//given
		List<Role> expected = new ArrayList<>() {{
			add(new Role(4L, "secretary"));
			add(new Role(5L, "courier"));
		}};
		//when
		List<Role> roles = new ArrayList<>() {{
			add(new Role("secretary"));
			add(new Role("courier"));
		}};
		List<Role> actual = repository.addAll(roles);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void add_validData_shouldAddRole() {
		//given
		Role expected = new Role(4L, "secretary");
		Role role = new Role("secretary");
		//when
		Role actual = repository.add(role);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void update_validData_shouldUpdateRole() {
		//given
		Role role = roles.get(0);
		Role expected = new Role(1L, "boss");
		//when
		role.setName("boss");
		Role actual = repository.update(role);
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
			add(new Role(1L, "admin"));
			add(new Role(3L, "customer"));
		}};
		//when
		List<Role>	actual = repository.findRolesByUser(1L);
		//then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void deleteRoleFromUser_validData_shouldDeleteRole() {
		// given
		Role role = roles.get(0);
		// when
		boolean actual = repository.deleteRoleFromUser(role.getId(), 1L);
		// then
		Assert.assertTrue(actual);
	}

}
