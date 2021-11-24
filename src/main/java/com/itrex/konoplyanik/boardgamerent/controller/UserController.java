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

import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserBaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserSaveDTO;
import com.itrex.konoplyanik.boardgamerent.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;

	@GetMapping
	public List<UserBaseDTO> findAll() {
		return userService.findAll();
	}

	@GetMapping("/{id}")
	public UserDTO findById(@PathVariable Long id) {
		return userService.findById(id);
	}
	
	
	@PostMapping
	public UserDTO add(@RequestBody UserSaveDTO user) {
		return userService.add(user);
	}
	
	@PutMapping
	public UserDTO update(@RequestBody UserSaveDTO user) {
		return userService.update(user);
	}
	
	@DeleteMapping("{id}")
	public boolean delete(@PathVariable long id) {
	   return userService.delete(id);
	}
	
	@GetMapping("/{id}/orders")
	public List<OrderListDTO> findOrdersByUser(@PathVariable Long id) {
		return userService.findOrdersByUser(id);
	}
	
	@GetMapping("/{id}/roles")
	public List<RoleDTO> findRolesByUser(@PathVariable Long id) {
		return userService.findRolesByUser(id);
	}
	
	@DeleteMapping("{userId}/roles/{roleId}")
	public boolean deleteRoleFromUser(@PathVariable long userId, @PathVariable long roleId) {
	   return userService.deleteRoleFromUser(userId, roleId);
	}
	
	@PostMapping("{userId}/roles/{roleId}")
	public RoleDTO addRoleToUser(@PathVariable long userId, @PathVariable long roleId) {
		return userService.addRoleToUser(userId, roleId);
	}

}
