package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.itrex.konoplyanik.boardgamerent.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.itrex.konoplyanik.boardgamerent.converters.OrderConverter;
import com.itrex.konoplyanik.boardgamerent.converters.RoleConverter;
import com.itrex.konoplyanik.boardgamerent.converters.UserConverter;
import com.itrex.konoplyanik.boardgamerent.dto.UserDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserSaveDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserUpdateDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.entity.User;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.service.BaseServiceTest;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceImplTest extends BaseServiceTest {

	@InjectMocks
	private UserServiceImpl userService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private RoleRepository roleRepository;
	@Mock
	private PurchaseRepository purchaseRepository;
	@Mock
	private RentRepository rentRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private OrderRepository orderRepository;

	@Test
	public void findAll_validData_shouldReturnUserBaseDTOList() throws ServiceException {
		// given
		int expected = 5;
		List<User> users = getUsers();
		Pageable pageable = PageRequest.of(1, 3, Sort.by("login").ascending());
		Page<User> userPages = new PageImpl<>(users);
		// when
		Mockito.when(userRepository.findAll(pageable)).thenReturn(userPages);
		int actual = userService.findAll(pageable).getSize();
		// then
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void findById_validData_shouldReturnUserDTO() throws ServiceException {
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
		Mockito.when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
		UserDTO actual = userService.findById(1L);
		// then
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void add_validData_shouldReturnNewUserDTO() throws ServiceException {
		// given
		User user = User.builder().login("alex").password("alex").name("Алекс").phone(2222222).email("alex@mail").build();
		User newUser = getUsers().get(1);
		Role role = getRoles().get(2);
		Set<Role> roles = new HashSet<>() {{
			add(role);
		}};
		Set<Order> orders = new HashSet<>();
		user.setRoles(roles);
		user.setOrders(orders);
		newUser.setRoles(roles);
		newUser.setOrders(orders);
		UserDTO expected = UserConverter.convertUserToDTO(newUser);
		UserSaveDTO userSaveDTO = UserSaveDTO.builder()
				.login(user.getLogin())
				.password(user.getPassword())
				.name(user.getName())
				.phone(user.getPhone())
				.email(user.getEmail())
				.roleIds(RoleConverter.convertRolesToRoleIds(roles))
				.build();
		// when
		Mockito.when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
		Mockito.when(userRepository.save(user)).thenReturn(newUser);
		Mockito.when(passwordEncoder.encode("alex")).thenReturn("alex");
		UserDTO actual = userService.add(userSaveDTO);
		// then
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void update_validData_shouldReturnNewUserDTO() throws ServiceException {
		// given
		User user = getUsers().get(1);
		User newUser = User.builder().id(2L).login("alex").password("alex").name("Алекс").phone(2222222).email("alex@mail").build();
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
		Mockito.when(userRepository.findById(userUpdateDTO.getId())).thenReturn(Optional.of(newUser));
		UserUpdateDTO actual = userService.update(userUpdateDTO);
		// then
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void delete_validData_shouldReturnTrue() throws ServiceException {
		//given
		User user = getUsers().get(0);
		Set<Role> roles = new HashSet<>() {{
			add(getRoles().get(0));
			add(getRoles().get(2));
		}};
		Order order = getOrders().get(0);
		order.setPurchases(new HashSet<>());
		order.setRents(new HashSet<>());
		Set<Order> orders = new HashSet<>() {{
			add(order);
		}};
		user.setRoles(roles);
		user.setOrders(orders);
		//when
		Mockito.when(purchaseRepository.findPurchasesByOrder_id(order.getId())).thenReturn(new ArrayList<>());
		Mockito.when(rentRepository.findRentsByOrder_id(order.getId())).thenReturn(new ArrayList<>());
        Mockito.when(userRepository.findUserById(1L)).thenReturn(Optional.of(user));
        //then
		Assertions.assertDoesNotThrow(() -> userService.delete(1L));
//		Assertions.assertTrue(userService.delete(1L));
	}
	
	@Test
	public void deleteRoleFromUser_validData_shouldReturnTrue() throws ServiceException {
		//given
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
		// when
		Mockito.when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
		Mockito.when(roleRepository.findById(getRoles().get(0).getId())).thenReturn(Optional.of(getRoles().get(0)));
		boolean actual = userService.deleteRoleFromUser(1L, getRoles().get(0).getId());
		// then
		Assertions.assertTrue(actual);
	}
	
	@Test
	public void addRoleToUser_validData_shouldReturnRoleDTO() throws ServiceException {
		//given
		User user = getUsers().get(0);
		Set<Role> roles = new HashSet<>() {{
			add(getRoles().get(0));
		}};
		Set<Order> orders = new HashSet<>() {{
			add(getOrders().get(0));
		}};
		Role role = getRoles().get(2);
		user.setRoles(roles);
		user.setOrders(orders);
		// when
		Mockito.when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
		Mockito.when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
		boolean actual = userService.addRoleToUser(user.getId(), role.getId());
		// then
		Assertions.assertTrue(actual);
	}

}
