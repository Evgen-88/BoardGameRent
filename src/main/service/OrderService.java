package com.itrex.konoplyanik.boardgamerent.service;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.dto.OrderDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderSaveDTO;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RentDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

public interface OrderService {

	List<OrderListDTO> findAll() throws ServiceException;
	OrderDTO findById(Long id) throws ServiceException;
	OrderDTO add(OrderSaveDTO order) throws ServiceException;
	OrderDTO update(OrderSaveDTO order) throws ServiceException;
	boolean delete(Long id) throws ServiceException;
	
	List<PurchaseDTO> findPurchasesByOrder(Long orderId) throws ServiceException;
	List<RentDTO> findRentsByOrder(Long orderId) throws ServiceException;
}
