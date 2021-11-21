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
import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.entity.User;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.repository.RoleRepository;
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
	@Mock
	private RoleRepository roleRepository;

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
		User user = getUsers().get(1);
		Set<Role> roles = new HashSet<>() {{
			add(getRoles().get(2));
		}};
		Set<Order> orders = new HashSet<>() {{
			add(getOrders().get(2));
		}};
		UserDTO expected = UserConverter.convertUserToDTO(getUsers().get(1));
		expected.setOrders(OrderConverter.convertToOrderListForUserDTO(orders));
		expected.setRoles(RoleConverter.convertFromSetRole(roles));
		// when
		Mockito.when(userRepository.findById(2L)).thenReturn(user);
		Mockito.when(roleRepository.findRolesByUser(2L)).thenReturn(new ArrayList<>(roles));
		Mockito.when(orderRepository.findOrdersByUser(2L)).thenReturn(new ArrayList<>(orders));
		UserDTO actual = userService.findById(2L);
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
		User newUser = User.builder().id(2L).login("alex").password("alex").name("Алекс").phone(2222222).email("alex@mail").build();
		newUser.setRoles(roles);
		UserDTO expected = UserConverter.convertUserToDTO(getUsers().get(1));
		expected.setRoles(RoleConverter.convertFromSetRole(roles));
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
		User newUser = User.builder().id(2L).login("alex").password("alex").name("Алекс").phone(2222222).email("alex@mail").build();
		newUser.setRoles(roles);
		UserDTO expected = UserConverter.convertUserToDTO(getUsers().get(1));
		expected.setRoles(RoleConverter.convertFromSetRole(roles));
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
		Mockito.when(userRepository.update(user)).thenReturn(newUser);
		UserDTO actual = userService.update(userSaveDTO);
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
	public void findOrdersByUser_validData_shouldReturnListOrderDTO() throws RepositoryException, ServiceException {
		//given
		Order order = getOrders().get(0);
        List<Order> orders = new ArrayList<>() {{
        	add(order);
        }};
        List<OrderListDTO> expected = new ArrayList<>() {{
        	add(OrderListDTO.builder()
        			.id(order.getId())
        			.totalPrice(order.getTotalPrice())
        			.date(order.getDate())
        			.status(order.getStatus())
        			.user(UserConverter.convertUserToBaseDTO(order.getUser()))
        			.build());
        }};
        // when
        Mockito.when(orderRepository.findOrdersByUser(1L)).thenReturn(orders);
        List<OrderListDTO> actual = userService.findOrdersByUser(1L);
        // then
        Assert.assertEquals(expected, actual);
	}

	@Test
	public void findRolesByUser_validData_shouldReturnRoleDTO() throws RepositoryException, ServiceException {
		//given
		Role role = getRoles().get(0);
        List<Role> roles = new ArrayList<>() {{
	       	add(role);
        }};
        List<RoleDTO> expected = new ArrayList<>() {{
	       	add(RoleDTO.builder()
	       			.id(role.getId())
	       			.name(role.getName())
		       		.build());
	    }};
		// when
		Mockito.when(roleRepository.findRolesByUser(1L)).thenReturn(roles);
		List<RoleDTO> actual = userService.findRolesByUser(1L);
		// then
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void findOrderListForUserByUser_validData_shouldReturnOrderListForUserDTO() throws RepositoryException, ServiceException {
		//given
		Order order = getOrders().get(0);
		List<Order> orders = new ArrayList<>() {{
        	add(order);
        }};
        List<OrderListForUserDTO> expected = new ArrayList<>() {{
        	add(OrderListForUserDTO.builder()
        			.id(order.getId())
		        	.totalPrice(order.getTotalPrice())
		        	.date(order.getDate())
		        	.status(order.getStatus())
		        	.build());
        }};
		// when
		Mockito.when(orderRepository.findOrdersByUser(1L)).thenReturn(orders);
		List<OrderListForUserDTO> actual = userService.findOrderListForUserByUser(1L);
		// then
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void deleteRolesFromUser_validData_shouldReturnTrue() throws RepositoryException, ServiceException {
		//given
		Role role = getRoles().get(0);
        List<Long> roleIds = new ArrayList<>() {{
	       	add(1L);
        }};
		// when
		Mockito.when(roleRepository.deleteRolesFromUser(1L, roleIds)).thenReturn(true);
		boolean actual = userService.deleteRolesFromUser(1L, roleIds);
		// then
		Assert.assertTrue(actual);
	}
	
	@Test
	public void addRolesToUser_validData_shouldReturnListRoleDTO() throws RepositoryException, ServiceException {
		//given
		Role role = getRoles().get(1);
		 List<Long> roleIds = new ArrayList<>() {{
		       	add(role.getId());
	        }};
        List<Role> roles = new ArrayList<>() {{
	       	add(role);
        }};
        List<RoleDTO> expected = new ArrayList<>() {{
	       	add(RoleDTO.builder()
	       			.id(role.getId())
	       			.name(role.getName())
		       		.build());
	    }};
		// when
		Mockito.when(roleRepository.addRolesToUser(1L, roleIds)).thenReturn(roles);
		List<RoleDTO> actual = userService.addRolesToUser(1L, roleIds);
		// then
		Assert.assertEquals(expected, actual);
	}

}
