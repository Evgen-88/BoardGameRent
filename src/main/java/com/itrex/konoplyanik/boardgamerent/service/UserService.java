package com.itrex.konoplyanik.boardgamerent.service;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.dto.UserSaveDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserBaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListForUserDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

public interface UserService {

	List<UserBaseDTO> findAll() throws ServiceException;
	UserDTO findById(Long id) throws ServiceException;
	UserDTO add(UserSaveDTO user) throws ServiceException;
	UserDTO update(UserSaveDTO user) throws ServiceException;
	boolean delete(Long id) throws ServiceException;
	
	List<OrderListDTO> findOrdersByUser(Long userId) throws ServiceException;
	List<RoleDTO> findRolesByUser(Long userId) throws ServiceException;
	List<OrderListForUserDTO> findOrderListForUserByUser(Long userId) throws ServiceException;
	boolean deleteRolesFromUser(Long userId, List<Long> roleIds) throws ServiceException;
	List<RoleDTO> addRolesToUser(Long userId, List<Long> roleIds) throws ServiceException;
}
