package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.itrex.konoplyanik.boardgamerent.converters.OrderConverter;
import com.itrex.konoplyanik.boardgamerent.converters.RoleConverter;
import com.itrex.konoplyanik.boardgamerent.converters.UserConverter;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListForUserDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserBaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserSaveDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserUpdateDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.entity.User;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.repository.UserRepository;
import com.itrex.konoplyanik.boardgamerent.service.BaseServiceTest;

@SpringBootTest
public class UserServiceImplTest extends BaseServiceTest {

	@InjectMocks
	private UserServiceImpl userService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private OrderRepository orderRepository;

	@Test
	public void findAll_validData_shouldReturnUserBaseDTOList() throws RepositoryException, ServiceException {
		// given
		List<User> users = getUsers();
		List<UserBaseDTO> expected = getUsers().stream().map(user -> UserConverter.convertUserToBaseDTO(user))
				.collect(Collectors.toList());
		// when
		Mockito.when(userRepository.findAll()).thenReturn(users);
		List<UserBaseDTO> actual = userService.findAll();
		// then
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void findById_validData_shouldReturnUserDTO() throws RepositoryException, ServiceException {
		// given
		User user = getUsers().get(0);
		Set<Role> roles = new HashSet<>() {{
			add(getRoles().get(0));
			add(getRoles().get(2));
		}};
		Set<Order> orders = new HashSet<>() {{
			add(getOrders().get(0));
		}};
		user.setRoles(roles);
		user.setOrders(orders);
		UserDTO expected = UserConverter.convertUserToDTO(user);
		expected.setOrders(OrderConverter.convertToOrderListForUserDTO(orders));
		expected.setRoles(RoleConverter.convertFromSetRole(roles));
		// when
		Mockito.when(userRepository.findById(1L)).thenReturn(user);
		UserDTO actual = userService.findById(1L);
		// then
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void add_validData_shouldReturnNewUserDTO() throws RepositoryException, ServiceException {
		// given
		User user = getUsers().get(1);
		Set<Role> roles = new HashSet<>() {{
			add(Role.builder().id(3L).build());
		}};
		Set<Order> orders = new HashSet<>();
		user.setRoles(roles);
		user.setOrders(orders);
		User newUser = User.builder().id(2L).login("alex").password("alex").name("Алекс").phone(2222222).email("alex@mail").build();
		newUser.setRoles(roles);
		newUser.setOrders(orders);
		UserDTO expected = UserConverter.convertUserToDTO(user);
		UserSaveDTO userSaveDTO = UserSaveDTO.builder()
				.id(user.getId())
				.login(user.getLogin())
				.password(user.getPassword())
				.name(user.getName())
				.phone(user.getPhone())
				.email(user.getEmail())
				.roleIds(RoleConverter.convertRolesToRoleIds(roles))
				.build();
		// when
		Mockito.when(userRepository.add(user, userSaveDTO.getRoleIds())).thenReturn(newUser);
		UserDTO actual = userService.add(userSaveDTO);
		// then
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void update_validData_shouldReturnNewUserDTO() throws RepositoryException, ServiceException {
		// given
		User user = getUsers().get(1);
		Set<Role> roles = new HashSet<>() {{
			add(Role.builder().id(3L).build());
		}};
		Set<Order> orders = new HashSet<>();
		user.setRoles(roles);
		user.setOrders(orders);
		User newUser = User.builder().id(2L).login("alex").password("alex").name("Алекс").phone(2222222).email("alex@mail").build();
		newUser.setRoles(roles);
		newUser.setOrders(orders);
		UserUpdateDTO expected = UserConverter.convertUserToUpdateDTO(user);
		UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
				.id(user.getId())
				.login(user.getLogin())
				.password(user.getPassword())
				.name(user.getName())
				.phone(user.getPhone())
				.email(user.getEmail())
				.build();
		// when
		Mockito.when(userRepository.update(user)).thenReturn(newUser);
		UserUpdateDTO actual = userService.update(userUpdateDTO);
		// then
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void delete_validData_shouldReturnTrue() throws RepositoryException, ServiceException {
		//given && when
        Mockito.when(userRepository.delete(1L)).thenReturn(true);
        //then
        Assert.assertTrue(userService.delete(1L));
	}
	
	@Test
	public void deleteRoleFromUser_validData_shouldReturnTrue() throws RepositoryException, ServiceException {
		//given
        Long roleId = getRoles().get(0).getId();
		// when
		Mockito.when(userRepository.deleteRoleFromUser(1L, roleId)).thenReturn(true);
		boolean actual = userService.deleteRoleFromUser(1L, roleId);
		// then
		Assert.assertTrue(actual);
	}
	
	@Test
	public void addRoleToUser_validData_shouldReturnRoleDTO() throws RepositoryException, ServiceException {
		//given
		Role role = getRoles().get(1);
		Long roleId = role.getId();
        RoleDTO expected = RoleDTO.builder()
	       			.id(role.getId())
	       			.name(role.getName())
		       		.build();
		// when
		Mockito.when(userRepository.addRoleToUser(1L, roleId)).thenReturn(role);
		RoleDTO actual = userService.addRoleToUser(1L, roleId);
		// then
		Assert.assertEquals(expected, actual);
	}

}
