package com.itrex.konoplyanik.boardgamerent.service;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.dto.PurchaseDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

public interface PurchaseService {

	PurchaseDTO findById(Long id) throws ServiceException;
	PurchaseDTO add(PurchaseDTO purchase) throws ServiceException;
	PurchaseDTO update(PurchaseDTO purchase) throws ServiceException;
	boolean delete(Long id) throws ServiceException;
	
	List<PurchaseDTO> findPurchasesByOrder(Long orderId) throws ServiceException;
}
