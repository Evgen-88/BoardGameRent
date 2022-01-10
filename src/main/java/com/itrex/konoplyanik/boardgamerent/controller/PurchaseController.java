package com.itrex.konoplyanik.boardgamerent.controller;

import com.itrex.konoplyanik.boardgamerent.dto.PurchaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseSaveDTO;
import com.itrex.konoplyanik.boardgamerent.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('PURCHASE_READ_WRITE') or hasAuthority('PURCHASE_DELETE')")
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

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('PURCHASE_DELETE')")
	public boolean delete(@PathVariable long id) {
		return purchaseService.delete(id);
	}

}
