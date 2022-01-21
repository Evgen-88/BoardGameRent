package com.itrex.konoplyanik.boardgamerent.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.itrex.konoplyanik.boardgamerent.service.OrderService;
import com.itrex.konoplyanik.boardgamerent.service.RoleService;
import com.itrex.konoplyanik.boardgamerent.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final RoleService roleService;
	private final OrderService orderService;

	@GetMapping
	public Page<UserBaseDTO> findAll(Pageable pageable) {
		Pageable page = PageRequest.of(0, 3, Sort.by("login").ascending());
		return userService.findAll(page);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('USER_READ')")
	public UserDTO findById(@PathVariable Long id) {
		return userService.findById(id);
	}

	@PostMapping
	public UserDTO add(@RequestBody UserSaveDTO user) {
		return userService.add(user);
	}

	@PutMapping
	public UserUpdateDTO update(@RequestBody UserUpdateDTO user) {
		return userService.update(user);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('USER_DELETE')")
	public boolean delete(@PathVariable long id) {
		return userService.delete(id);
	}

	@GetMapping("/{id}/orders")
	@PreAuthorize("hasAuthority('USER_READ')")
	public List<OrderListDTO> findOrdersByUser(@PathVariable Long id) {
		return orderService.findOrdersByUser(id);
	}

	@GetMapping("/{id}/roles")
	@PreAuthorize("hasAuthority('ROLE_READ')")
	public List<RoleDTO> findRolesByUser(@PathVariable Long id) {
		return roleService.findRolesByUser(id);
	}

	@DeleteMapping("/{id}/roles")
	@PreAuthorize("hasAuthority('ROLE_READ')")
	public boolean deleteRoleFromUser(@PathVariable Long id, @RequestParam long roleId) {
		return userService.deleteRoleFromUser(id, roleId);
	}

	@PutMapping("/{id}/roles")
	@PreAuthorize("hasAuthority('ROLE_READ')")
	public boolean addRoleToUser(@PathVariable Long id, @RequestParam long roleId) {
		return userService.addRoleToUser(id, roleId);
	}

}
