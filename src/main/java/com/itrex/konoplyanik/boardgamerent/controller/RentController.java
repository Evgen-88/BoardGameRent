package com.itrex.konoplyanik.boardgamerent.controller;

import com.itrex.konoplyanik.boardgamerent.dto.RentDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RentSaveDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rents")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('RENT_READ_WRITE') or hasAuthority('RENT_DELETE')")
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
	@PreAuthorize("hasAuthority('RENT_DELETE')")
	public ResponseEntity<Boolean> delete(@PathVariable long id) {
		try {
			rentService.delete(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

}
