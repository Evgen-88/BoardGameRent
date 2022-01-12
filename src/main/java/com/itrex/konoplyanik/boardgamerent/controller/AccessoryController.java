package com.itrex.konoplyanik.boardgamerent.controller;

import com.itrex.konoplyanik.boardgamerent.dto.AccessoryDTO;
import com.itrex.konoplyanik.boardgamerent.service.AccessoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;


import java.util.List;

@RestController
@RequestMapping("/accessories")
@RequiredArgsConstructor
public class AccessoryController {

	private final AccessoryService accessoryService;

	@GetMapping
	public List<AccessoryDTO> findAll() {
		return  accessoryService.findAll();
	}

	@GetMapping("/{id}")
	public AccessoryDTO findById(@PathVariable Long id) {
		return accessoryService.findById(id);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ACCESSORY_WRITE_DELETE')")
	public AccessoryDTO add(@RequestBody AccessoryDTO accessory) {
		return accessoryService.add(accessory);
	}

	@PutMapping
	@PreAuthorize("hasAuthority('ACCESSORY_WRITE_DELETE')")
	public AccessoryDTO update(@RequestBody AccessoryDTO accessory) {
		return accessoryService.update(accessory);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ACCESSORY_WRITE_DELETE')")
	public boolean delete(@PathVariable(name = "id") long id) {
		return accessoryService.delete(id);
	}

}