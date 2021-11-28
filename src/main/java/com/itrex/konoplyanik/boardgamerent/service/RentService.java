package com.itrex.konoplyanik.boardgamerent.service;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.dto.RentDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RentSaveDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

public interface RentService {

	RentDTO findById(Long id) throws ServiceException;
	RentDTO add(RentSaveDTO rent) throws ServiceException;
	RentDTO update(RentSaveDTO rent) throws ServiceException;
	boolean delete(Long id) throws ServiceException;
	
	List<RentDTO> findRentsByOrder(Long orderId) throws ServiceException;
}
