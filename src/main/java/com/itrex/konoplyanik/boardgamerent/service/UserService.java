package com.itrex.konoplyanik.boardgamerent.service;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.dto.UserDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserListDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

public interface UserService {

	List<UserListDTO> findAll() throws ServiceException;
	UserDTO findById(Long id) throws ServiceException;
	UserDTO add(UserDTO user) throws ServiceException;
	UserDTO update(UserDTO user) throws ServiceException;
	boolean delete(Long id) throws ServiceException;
}
