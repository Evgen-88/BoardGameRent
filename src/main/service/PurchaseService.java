package com.itrex.konoplyanik.boardgamerent.service;

import com.itrex.konoplyanik.boardgamerent.dto.PurchaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseSaveDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

public interface PurchaseService {

	PurchaseDTO findById(Long id) throws ServiceException;
	PurchaseDTO add(PurchaseSaveDTO purchase) throws ServiceException;
	PurchaseDTO update(PurchaseSaveDTO purchase) throws ServiceException;
	boolean delete(Long id) throws ServiceException;
	
}
