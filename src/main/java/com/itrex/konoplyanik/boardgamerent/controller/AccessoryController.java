package com.itrex.konoplyanik.boardgamerent.controller;

import com.itrex.konoplyanik.boardgamerent.dto.AccessoryDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.service.AccessoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accessories")
@RequiredArgsConstructor
public class AccessoryController {

	private final AccessoryService accessoryService;

	@GetMapping
	public ResponseEntity<List<AccessoryDTO>> findAll() {
		List<AccessoryDTO> accessories = null;
		try {
			accessories = accessoryService.findAll();
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return accessories != null && !accessories.isEmpty() ? new ResponseEntity<>(accessories, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccessoryDTO> findById(@PathVariable Long id) {
		AccessoryDTO accessory = null;
		try {
			accessory = accessoryService.findById(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return accessory != null ? new ResponseEntity<>(accessory, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ACCESSORY_WRITE_DELETE')")
	public ResponseEntity<AccessoryDTO> add(@RequestBody AccessoryDTO accessory) {
		AccessoryDTO accessoryDTO = null;
		try {
			accessoryDTO = accessoryService.add(accessory);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(accessoryDTO, HttpStatus.CREATED);
	}

	@PutMapping
	@PreAuthorize("hasAuthority('ACCESSORY_WRITE_DELETE')")
	public ResponseEntity<AccessoryDTO> update(@RequestBody AccessoryDTO accessory) {
		AccessoryDTO accessoryDTO = null;
		try {
			accessoryDTO = accessoryService.update(accessory);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return accessoryDTO != null ? new ResponseEntity<>(accessoryDTO, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ACCESSORY_WRITE_DELETE')")
	public ResponseEntity<Boolean> delete(@PathVariable(name = "id") long id) {
		try {
			accessoryService.delete(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

}