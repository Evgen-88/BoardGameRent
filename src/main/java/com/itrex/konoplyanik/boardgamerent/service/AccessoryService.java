package com.itrex.konoplyanik.boardgamerent.service;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.dto.AccessoryDTO;
import com.itrex.konoplyanik.boardgamerent.dto.AccessoryListDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

public interface AccessoryService {

	List<AccessoryListDTO> findAll() throws ServiceException;
	AccessoryDTO findById(Long id) throws ServiceException;
	AccessoryListDTO findListById(Long id) throws ServiceException;
	AccessoryDTO add(AccessoryDTO accessory) throws ServiceException;
	AccessoryDTO update(AccessoryDTO accessory) throws ServiceException;
	boolean delete(Long id) throws ServiceException;
}
