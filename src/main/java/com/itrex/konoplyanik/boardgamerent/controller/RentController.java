package com.itrex.konoplyanik.boardgamerent.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itrex.konoplyanik.boardgamerent.dto.RentDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RentSaveDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.service.RentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rents")
@RequiredArgsConstructor
public class RentController {

	private final RentService rentService;

	@GetMapping("/{id}")
	public ResponseEntity<RentDTO> findById(@PathVariable Long id) {
		RentDTO rent = null;
		try {
			rent = rentService.findById(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return rent != null ? new ResponseEntity<>(rent, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<RentDTO> add(@RequestBody RentSaveDTO rent) {
		RentDTO rentDTO = null;
		try {
			rentDTO = rentService.add(rent);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(rentDTO, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<RentDTO> update(@RequestBody RentSaveDTO rent) {
		RentDTO rentDTO = null;
		try {
			rentDTO = rentService.update(rent);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return rentDTO != null ? new ResponseEntity<>(rentDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable long id) {
		try {
			rentService.delete(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

}
