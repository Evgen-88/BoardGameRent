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

import com.itrex.konoplyanik.boardgamerent.dto.AccessoryDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.service.AccessoryService;

import lombok.RequiredArgsConstructor;

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
	@Secured("manager")
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
	@Secured("manager")
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
	@Secured("manager")
	public ResponseEntity<Boolean> delete(@PathVariable(name = "id") long id) {
		try {
			accessoryService.delete(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

}