package com.itrex.konoplyanik.boardgamerent.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itrex.konoplyanik.boardgamerent.dto.UserSaveDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserUpdateDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserAuthenticationDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserBaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

public interface UserService {

	Page<UserBaseDTO> findAll(Pageable pageable);
	UserDTO findById(Long id) throws ServiceException;
	UserAuthenticationDTO findByLogin(String login) throws ServiceException;
	UserDTO add(UserSaveDTO user) throws ServiceException;
	UserUpdateDTO update(UserUpdateDTO user) throws ServiceException;
	boolean delete(Long id) throws ServiceException;
	
	boolean deleteRoleFromUser(Long userId, Long roleId) throws ServiceException;
	boolean addRoleToUser(Long userId, Long roleId) throws ServiceException;
}
