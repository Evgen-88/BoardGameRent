package com.itrex.konoplyanik.boardgamerent.service;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.dto.OrderDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;

public interface OrderService {

	List<OrderListDTO> findAll() throws ServiceException;
	OrderDTO findById(Long id) throws ServiceException;
	OrderDTO add(OrderDTO order) throws ServiceException;
	OrderDTO update(OrderDTO order) throws ServiceException;
	boolean delete(Long id) throws ServiceException;
	
	List<OrderListDTO> findOrdersByUser(Long userId) throws ServiceException;
}
