package com.itrex.konoplyanik.boardgamerent.controller;

import com.itrex.konoplyanik.boardgamerent.dto.PurchaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseSaveDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('PURCHASE_READ_WRITE') or hasAuthority('PURCHASE_DELETE')")
public class PurchaseController {

	private final PurchaseService purchaseService;

	@GetMapping("/{id}")
	public ResponseEntity<PurchaseDTO> findById(@PathVariable Long id) {
		PurchaseDTO purchase = null;
		try {
			purchase = purchaseService.findById(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return purchase != null ? new ResponseEntity<>(purchase, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<PurchaseDTO> add(@RequestBody PurchaseSaveDTO purchase) {
		PurchaseDTO purchaseDTO = null;
		try {
			purchaseDTO = purchaseService.add(purchase);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(purchaseDTO, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<PurchaseDTO> update(@RequestBody PurchaseSaveDTO purchase) {
		PurchaseDTO purchaseDTO = null;
		try {
			purchaseDTO = purchaseService.update(purchase);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return purchaseDTO != null ? new ResponseEntity<>(purchaseDTO, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('PURCHASE_DELETE')")
	public ResponseEntity<Boolean> delete(@PathVariable long id) {
		try {
			purchaseService.delete(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

}
