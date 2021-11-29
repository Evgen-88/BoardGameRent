package com.itrex.konoplyanik.boardgamerent.service;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.dto.OrderDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderSaveDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

public interface OrderService {

	List<OrderListDTO> findAll();
	OrderDTO findById(Long id) throws ServiceException;
	OrderSaveDTO add(OrderSaveDTO order) throws ServiceException;
	OrderSaveDTO update(OrderSaveDTO order) throws ServiceException;
	boolean delete(Long id) throws ServiceException;
	
	List<OrderListDTO> findOrdersByUser(Long userId);
}
