package com.itrex.konoplyanik.boardgamerent.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itrex.konoplyanik.boardgamerent.dto.PurchaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseSaveDTO;
import com.itrex.konoplyanik.boardgamerent.service.PurchaseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

	private final PurchaseService purchaseService;

	@GetMapping("/{id}")
	public PurchaseDTO findById(@PathVariable Long id) {
		return purchaseService.findById(id);
	}
	
	
	@PostMapping
	public PurchaseDTO add(@RequestBody PurchaseSaveDTO purchase) {
		return purchaseService.add(purchase);
	}
	
	@PutMapping
	public PurchaseDTO update(@RequestBody PurchaseSaveDTO purchase) {
		return purchaseService.update(purchase);
	}
	
	@DeleteMapping("{id}")
	public boolean delete(@PathVariable long id) {
	   return purchaseService.delete(id);
	}
	
}
