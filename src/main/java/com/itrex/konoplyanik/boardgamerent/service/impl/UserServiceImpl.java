package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itrex.konoplyanik.boardgamerent.converters.UserConverter;
import com.itrex.konoplyanik.boardgamerent.dto.UserAuthenticationDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserBaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserSaveDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserUpdateDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.entity.User;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.OrderRepository;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;
import com.itrex.konoplyanik.boardgamerent.repository.RentRepository;
import com.itrex.konoplyanik.boardgamerent.repository.RoleRepository;
import com.itrex.konoplyanik.boardgamerent.repository.UserRepository;
import com.itrex.konoplyanik.boardgamerent.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final OrderRepository orderRepository;
	private final PurchaseRepository purchaseRepository;
	private final RentRepository rentRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional(readOnly = true)
	public Page<UserBaseDTO> findAll(Pageable pageable) {
		Page<User> pageUsers = userRepository.findAll(pageable);
		List<UserBaseDTO> users = pageUsers.stream().map(UserConverter::convertUserToBaseDTO).collect(Collectors.toList());
		return new PageImpl<>(users);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) throws ServiceException {
		return userRepository.findUserById(id).map(UserConverter::convertUserToDTO)
				.orElseThrow(() -> new ServiceException("User not found"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserAuthenticationDTO findByLogin(String login) throws ServiceException {
		return userRepository.findUserByLogin(login).map(UserConverter::convertUserToAuthenticationDTO).orElseThrow(() -> new ServiceException("User not found"));
	}

	@Override
	@Transactional
	public UserDTO add(UserSaveDTO userDTO) throws ServiceException {
		Set<Role> roles = new HashSet<>();
		for (Long roleId : userDTO.getRoleIds()) {
			roles.add(roleRepository.findById(roleId).get());
		}
		User user = User.builder().login(userDTO.getLogin()).password(userDTO.getPassword()).name(userDTO.getName())
				.email(userDTO.getEmail()).phone(userDTO.getPhone()).roles(roles).orders(new HashSet<>()).build();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return UserConverter.convertUserToDTO(userRepository.save(user));
	}

	@Override
	@Transactional
	public UserUpdateDTO update(UserUpdateDTO userDTO) throws ServiceException {
		User user = userRepository.findById(userDTO.getId())
				.orElseThrow(() -> new ServiceException("User not found"));
		if (userDTO.getLogin() != null) {
			user.setLogin(userDTO.getLogin());
		}
		if (userDTO.getPassword() != null) {
			user.setPassword(userDTO.getPassword());
		}
		if (userDTO.getName() != null) {
			user.setName(userDTO.getName());
		}
		if (userDTO.getEmail() != null) {
			user.setEmail(userDTO.getEmail());
		}
		if (userDTO.getPhone() != null) {
			user.setPhone(userDTO.getPhone());
		}
		userRepository.flush();
		return UserConverter.convertUserToUpdateDTO(userRepository.findById(userDTO.getId()).get());
	}

	@Override
	@Transactional
	public boolean delete(Long id) throws ServiceException {
		User user = userRepository.findUserById(id).orElseThrow(() -> new ServiceException("User not found"));
		for (Order order : user.getOrders()) {
			for (Purchase purchase : purchaseRepository.findPurchasesByOrder_id(order.getId())) {
				purchaseRepository.delete(purchase);
			}
			for (Rent rent : rentRepository.findRentsByOrder_id(order.getId())) {
				rentRepository.delete(rent);
			}
			orderRepository.delete(order);
		}
		userRepository.delete(user);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteRoleFromUser(Long userId, Long roleId) throws ServiceException {
		User user = userRepository.findUserById(userId).orElseThrow(() -> new ServiceException("User not found"));
		Role role = roleRepository.findById(roleId).orElseThrow(() -> new ServiceException("Role not found"));
		user.getRoles().remove(role);
		return true;
	}

	@Override
	@Transactional
	public boolean addRoleToUser(Long userId, Long roleId) throws ServiceException {
		User user = userRepository.findUserById(userId).orElseThrow(() -> new ServiceException("User not found"));
		Role role = roleRepository.findById(roleId).orElseThrow(() -> new ServiceException("Role not found"));
		user.getRoles().add(role);
		userRepository.flush();
		return true;
	}

}
