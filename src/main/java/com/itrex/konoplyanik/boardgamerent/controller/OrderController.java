package com.itrex.konoplyanik.boardgamerent.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itrex.konoplyanik.boardgamerent.dto.OrderDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderSaveDTO;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RentDTO;
import com.itrex.konoplyanik.boardgamerent.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;

	@GetMapping
	public List<OrderListDTO> findAll() {
		return orderService.findAll();
	}

	@GetMapping("/{id}")
	public OrderDTO findById(@PathVariable Long id) {
		return orderService.findById(id);
	}
	
	@PostMapping
	public OrderDTO add(@RequestBody OrderSaveDTO order) {
		return orderService.add(order);
	}
	
	@PutMapping
	public OrderDTO update(@RequestBody OrderSaveDTO order) {
		return orderService.update(order);
	}
	
	@DeleteMapping("{id}")
	public boolean delete(@PathVariable long id) {
	   return orderService.delete(id);
	}
	
	@GetMapping("/{id}/purchases")
	public List<PurchaseDTO> findPurchasesByOrder(@PathVariable Long id) {
		return orderService.findPurchasesByOrder(id);
	}
	
	@GetMapping("/{id}/rents")
	public List<RentDTO> findRentsByOrder(@PathVariable Long id) {
		return orderService.findRentsByOrder(id);
	}

}
