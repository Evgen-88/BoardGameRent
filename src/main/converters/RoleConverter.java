package com.itrex.konoplyanik.boardgamerent.converters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Role;

public class RoleConverter {

	public static Role convertRoleToEntity(RoleDTO roleDTO) {
		return Role.builder()
				.id(roleDTO.getId())
				.name(roleDTO.getName())
				.build();
	}
	
	public static Set<Role> convertRoleIdsToEntity(List<Long> roleIds) {
		return roleIds.stream().map(roleId -> Role.builder().id(roleId).build()).collect(Collectors.toSet());
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
	
	public static List<RoleDTO> convertFromSetRole(Set<Role> roles) {
		List<RoleDTO> rolesDTO = new ArrayList<>();
		for (Role role : roles) {
			rolesDTO.add(RoleConverter.convertRoleToDTO(role));
		}
		return rolesDTO;
	}
	
	public static List<Long> convertRolesToRoleIds(Set<Role> roles) {
		List<Long> roleIds = new ArrayList<>();
		for (Role role : roles) {
			roleIds.add(role.getId());
		}
		return roleIds;
	}
	
}
