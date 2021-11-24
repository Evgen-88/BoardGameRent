package com.itrex.konoplyanik.boardgamerent.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
	
	private final RoleService roleService;

	@GetMapping
	public List<RoleDTO> findAll() {
		return roleService.findAll();
	}

}
