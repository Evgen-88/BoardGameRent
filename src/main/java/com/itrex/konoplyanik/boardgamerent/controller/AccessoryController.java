package com.itrex.konoplyanik.boardgamerent.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itrex.konoplyanik.boardgamerent.dto.AccessoryDTO;
import com.itrex.konoplyanik.boardgamerent.service.AccessoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/accessories")
@RequiredArgsConstructor
public class AccessoryController {

	private final AccessoryService accessoryService;

	@GetMapping
	public List<AccessoryDTO> findAll() {
		return accessoryService.findAll();
	}

	@GetMapping("/{id}")
	public AccessoryDTO findById(@PathVariable Long id) {
		return accessoryService.findById(id);
	}
	
	
	@PostMapping
	public AccessoryDTO add(@RequestBody AccessoryDTO accessory) {
		return accessoryService.add(accessory);
	}
	
	@PutMapping
	public AccessoryDTO update(@RequestBody AccessoryDTO accessory) {
		return accessoryService.update(accessory);
	}
	
	@DeleteMapping("{id}")
	public boolean delete(@PathVariable long id) {
	   return accessoryService.delete(id);
	}
	
}