package com.itrex.konoplyanik.boardgamerent.service;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

public interface RoleService {

	List<RoleDTO> findAll() throws ServiceException;
	List<RoleDTO> findRolesByUser(Long userId) throws ServiceException;
	
}
