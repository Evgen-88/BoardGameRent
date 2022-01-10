package com.itrex.konoplyanik.boardgamerent.controller;

import com.itrex.konoplyanik.boardgamerent.dto.*;
import com.itrex.konoplyanik.boardgamerent.service.OrderService;
import com.itrex.konoplyanik.boardgamerent.service.PurchaseService;
import com.itrex.konoplyanik.boardgamerent.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ORDER_READ_WRITE') or hasAuthority('ORDER_DELETE')")
public class OrderController {

	private final OrderService orderService;
	private final PurchaseService purchaseService;
	private final RentService rentService;

	@GetMapping
	public List<OrderListDTO> findAll() {
		return orderService.findAll();
	}

	@GetMapping("/{id}")
	public OrderDTO findById(@PathVariable Long id) {
		return orderService.findById(id);
	}

	@PostMapping
	public OrderSaveDTO add(@RequestBody OrderSaveDTO order) {
		return orderService.add(order);
	}

	@PutMapping
	public OrderSaveDTO update(@RequestBody OrderSaveDTO order) {
		return orderService.update(order);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ORDER_DELETE')")
	public boolean delete(@PathVariable long id) {
		return orderService.delete(id);
	}

	@GetMapping("/{id}/purchases")
	public List<PurchaseDTO> findPurchasesByOrder(@PathVariable Long id) {
		return purchaseService.findPurchasesByOrder(id);
	}

	@GetMapping("/{id}/rents")
	public List<RentDTO> findRentsByOrder(@PathVariable Long id) {
		return rentService.findRentsByOrder(id);
	}

}
