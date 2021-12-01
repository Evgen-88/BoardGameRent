package com.itrex.konoplyanik.boardgamerent.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.service.OrderService;
import com.itrex.konoplyanik.boardgamerent.service.PurchaseService;
import com.itrex.konoplyanik.boardgamerent.service.RentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Secured({"manager", "customer"})
public class OrderController {

	private final OrderService orderService;
	private final PurchaseService purchaseService;
	private final RentService rentService;

	@GetMapping
	public ResponseEntity<List<OrderListDTO>> findAll() {
		List<OrderListDTO> orders = null;
		try {
			orders = orderService.findAll();
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return orders != null && !orders.isEmpty() ? new ResponseEntity<>(orders, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
		OrderDTO order = null;
		try {
			order = orderService.findById(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return order != null ? new ResponseEntity<>(order, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	@Secured("customer")
	public ResponseEntity<OrderSaveDTO> add(@RequestBody OrderSaveDTO order) {
		OrderSaveDTO orderDTO = null;
		try {
			orderDTO = orderService.add(order);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<OrderSaveDTO> update(@RequestBody OrderSaveDTO order) {
		OrderSaveDTO orderDTO = null;
		try {
			orderDTO = orderService.update(order);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return orderDTO != null ? new ResponseEntity<>(orderDTO, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	@DeleteMapping("/{id}")
	@Secured("manager")
	public ResponseEntity<Boolean> delete(@PathVariable long id) {
		try {
			orderService.delete(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@GetMapping("/{id}/purchases")
	public ResponseEntity<List<PurchaseDTO>> findPurchasesByOrder(@PathVariable Long id) {
		List<PurchaseDTO> purchases = null;
		try {
			purchases = purchaseService.findPurchasesByOrder(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return purchases != null && !purchases.isEmpty() ? new ResponseEntity<>(purchases, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{id}/rents")
	public ResponseEntity<List<RentDTO>> findRentsByOrder(@PathVariable Long id) {
		List<RentDTO> rents = null;
		try {
			rents = rentService.findRentsByOrder(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return rents != null && !rents.isEmpty() ? new ResponseEntity<>(rents, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
