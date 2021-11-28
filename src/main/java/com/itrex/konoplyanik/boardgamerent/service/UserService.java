package com.itrex.konoplyanik.boardgamerent.service;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.dto.UserSaveDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserUpdateDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserBaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

public interface UserService {

	List<UserBaseDTO> findAll() throws ServiceException;
	UserDTO findById(Long id) throws ServiceException;
	UserDTO add(UserSaveDTO user) throws ServiceException;
	UserUpdateDTO update(UserUpdateDTO user) throws ServiceException;
	boolean delete(Long id) throws ServiceException;
	
	boolean deleteRoleFromUser(Long userId, Long roleId) throws ServiceException;
	RoleDTO addRoleToUser(Long userId, Long roleId) throws ServiceException;
}
