package com.itrex.konoplyanik.boardgamerent.converters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Role;

public class RoleConverter {

	public static Role convertRoleToEntity(RoleDTO roleDTO) {
		Role role = new Role();
		role.setId(roleDTO.getId());
		role.setName(roleDTO.getName());
		return role;
	}
	
	public static RoleDTO convertRoleToDTO(Role role) {
		return RoleDTO.builder()
				.id(role.getId())
				.name(role.getName())
				.build();
	}
	
	public static Set<Role> convertRolesToEntity(List<RoleDTO> rolesDTO) {
		Set<Role> roles = new HashSet<>();
		for (RoleDTO roleDTO: rolesDTO) {
			roles.add(RoleConverter.convertRoleToEntity(roleDTO));
		}
		return roles;
	}
	
	public static List<RoleDTO> convertRolesToDTO(List<Role> roles) {
		List<RoleDTO> rolesDTO = new ArrayList<>();
		for (Role role : roles) {
			rolesDTO.add(RoleConverter.convertRoleToDTO(role));
		}
		return rolesDTO;
	}
	
	public static List<Long> convertRoleDTOToId(List<RoleDTO> roles) {
		List<Long> roleIds = new ArrayList<>();
		for(RoleDTO role : roles) {
			roleIds.add(role.getId());
		}
		return roleIds;
	}
	
}
