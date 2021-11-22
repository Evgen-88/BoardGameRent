package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itrex.konoplyanik.boardgamerent.dto.UserSaveDTO;
import com.itrex.konoplyanik.boardgamerent.converters.OrderConverter;
import com.itrex.konoplyanik.boardgamerent.converters.RoleConverter;
import com.itrex.konoplyanik.boardgamerent.converters.UserConverter;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserBaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListForUserDTO;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.repository.RoleRepository;
import com.itrex.konoplyanik.boardgamerent.repository.UserRepository;
import com.itrex.konoplyanik.boardgamerent.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final OrderRepository orderRepository;
	private final RoleRepository roleRepository;
	
	public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public List<UserBaseDTO> findAll() throws ServiceException {
		try {
			return userRepository.findAll().stream()
					.map(UserConverter::convertUserToBaseDTO)
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findAll: " + ex);
		}
	}

	@Override
	public UserDTO findById(Long id) throws ServiceException {
		try {
			UserDTO user =  UserConverter.convertUserToDTO(userRepository.findById(id));
			user.setRoles(RoleConverter.convertRolesToDTO(roleRepository.findRolesByUser(id)));
			user.setOrders(orderRepository.findOrdersByUser(id).stream()
					.map(OrderConverter::convertOrderToListForUserDTO)
					.collect(Collectors.toList()));
			return user;
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findById: " + ex);
		}
	}

	@Override
	public UserDTO add(UserSaveDTO user) throws ServiceException {
		try {
			UserDTO userDTO = UserConverter.convertUserToDTO(userRepository.add(UserConverter.convertUserToEntity(user), user.getRoleIds()));
			List<RoleDTO> roles = user.getRoleIds().stream().map(roleId -> RoleDTO.builder().id(roleId).build()).collect(Collectors.toList());
			userDTO.setRoles(roles);
			return userDTO;
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: add: " + ex);
		}
	}

	@Override
	public UserDTO update(UserSaveDTO user) throws ServiceException {
		try {
			UserDTO userDTO = UserConverter.convertUserToDTO(userRepository.update(UserConverter.convertUserToEntity(user)));
			List<RoleDTO> roles = user.getRoleIds().stream().map(roleId -> RoleDTO.builder().id(roleId).build()).collect(Collectors.toList());
			userDTO.setRoles(roles);
			return userDTO;
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: update: " + ex);
		}
	}

	@Override
	public boolean delete(Long id) throws ServiceException {
		try {
			return userRepository.delete(id);
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: update: " + ex);
		}
	}
	
	@Override
	public List<OrderListDTO> findOrdersByUser(Long userId) throws ServiceException {
		try {
			return orderRepository.findOrdersByUser(userId).stream()
					.map(OrderConverter::convertOrderToListDTO)
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findOrdersByUser: " + ex);
		}
	}
	
	@Override
	public List<RoleDTO> findRolesByUser(Long userId) throws ServiceException {
		try {
			return roleRepository.findRolesByUser(userId).stream()
					.map(RoleConverter::convertRoleToDTO)
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findOrdersByUser: " + ex);
		}
	}

	@Override
	public List<OrderListForUserDTO> findOrderListForUserByUser(Long userId) throws ServiceException {
		try {
			return orderRepository.findOrdersByUser(userId).stream()
					.map(OrderConverter::convertOrderToListForUserDTO)
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findOrdersByUser: " + ex);
		}
	}

	@Override
	public boolean deleteRolesFromUser(Long userId, List<Long> roleIds) throws ServiceException {
		try {
			return roleRepository.deleteRolesFromUser(userId, roleIds);
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: deleteRolesFromUser: " + ex);
		}
	}

	@Override
	public List<RoleDTO> addRolesToUser(Long userId, List<Long> roleIds) throws ServiceException {
		try {
			return RoleConverter.convertRolesToDTO(roleRepository.addRolesToUser(userId, roleIds));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: addRolesToUser: " + ex);
		}
	}

}
