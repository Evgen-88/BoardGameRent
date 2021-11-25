package com.itrex.konoplyanik.boardgamerent.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserBaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserSaveDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserUpdateDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity<List<UserBaseDTO>> findAll() {
		List<UserBaseDTO> users = null;
		try {
			users = userService.findAll();
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return users != null && !users.isEmpty() ? new ResponseEntity<>(users, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO user = null;
		try {
			user = userService.findById(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<UserDTO> add(@RequestBody UserSaveDTO user) {
		UserDTO userDTO = null;
		try {
			userDTO = userService.add(user);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<UserUpdateDTO> update(@RequestBody UserUpdateDTO user) {
		UserUpdateDTO userDTO = null;
		try {
			userDTO = userService.update(user);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return userDTO != null ? new ResponseEntity<>(userDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable long id) {
		try {
			userService.delete(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@GetMapping("/{id}/orders")
	public ResponseEntity<List<OrderListDTO>> findOrdersByUser(@PathVariable Long id) {
		List<OrderListDTO> orders = null;
		try {
			orders = userService.findOrdersByUser(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return orders != null && !orders.isEmpty() ? new ResponseEntity<>(orders, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{id}/roles")
	public ResponseEntity<List<RoleDTO>> findRolesByUser(@PathVariable Long id) {
		List<RoleDTO> roles = null;
		try {
			roles = userService.findRolesByUser(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return roles != null && !roles.isEmpty() ? new ResponseEntity<>(roles, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping
	public ResponseEntity<Boolean> deleteRoleFromUser(@RequestParam long userId, @RequestParam long roleId) {
		try {
			userService.deleteRoleFromUser(userId, roleId);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@PutMapping("/roles")
	public ResponseEntity<RoleDTO> addRoleToUser(@RequestParam long userId, @RequestParam long roleId) {
		RoleDTO role = null;
		try {
			role = userService.addRoleToUser(userId, roleId);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(role, HttpStatus.CREATED);
	}

}
