package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itrex.konoplyanik.boardgamerent.dto.UserSaveDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserUpdateDTO;
import com.itrex.konoplyanik.boardgamerent.converters.RoleConverter;
import com.itrex.konoplyanik.boardgamerent.converters.UserConverter;
import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserBaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserDTO;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.UserRepository;
import com.itrex.konoplyanik.boardgamerent.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
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
			return UserConverter.convertUserToDTO(userRepository.findById(id));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findById: " + ex);
		}
	}

	@Override
	public UserDTO add(UserSaveDTO user) throws ServiceException {
		try {
			return UserConverter.convertUserToDTO(userRepository.add(UserConverter.convertUserToEntity(user), user.getRoleIds()));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: add: " + ex);
		}
	}

	@Override
	public UserUpdateDTO update(UserUpdateDTO user) throws ServiceException {
		try {
			return UserConverter.convertUserToUpdateDTO(userRepository.update(UserConverter.convertUserUpdateToEntity(user)));
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
	public boolean deleteRoleFromUser(Long userId, Long roleId) throws ServiceException {
		try {
			return userRepository.deleteRoleFromUser(userId, roleId);
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: deleteRolesFromUser: " + ex);
		}
	}

	@Override
	public RoleDTO addRoleToUser(Long userId, Long roleId) throws ServiceException {
		try {
			return RoleConverter.convertRoleToDTO(userRepository.addRoleToUser(userId, roleId));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: addRolesToUser: " + ex);
		}
	}

}
