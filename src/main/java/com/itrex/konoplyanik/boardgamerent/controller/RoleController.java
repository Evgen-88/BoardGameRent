package com.itrex.konoplyanik.boardgamerent.controller;

import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_READ')")
public class RoleController {

	private final RoleService roleService;

	@GetMapping
	public ResponseEntity<List<RoleDTO>> findAll() {
		List<RoleDTO> roles = null;
		try {
			roles = roleService.findAll();
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return roles != null && !roles.isEmpty() ? new ResponseEntity<>(roles, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
