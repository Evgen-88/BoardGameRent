package com.itrex.konoplyanik.boardgamerent.service;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.dto.AccessoryDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

public interface AccessoryService {

	List<AccessoryDTO> findAll();
	AccessoryDTO findById(Long id) throws ServiceException;
	AccessoryDTO add(AccessoryDTO accessory);
	AccessoryDTO update(AccessoryDTO accessory) throws ServiceException;
	boolean delete(Long id) throws ServiceException;
}
