package com.itrex.konoplyanik.boardgamerent.converters;

import com.itrex.konoplyanik.boardgamerent.dto.UserBaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserSaveDTO;
import com.itrex.konoplyanik.boardgamerent.entity.User;

public class UserConverter {

	public static User convertUserToEntity(UserSaveDTO userDTO) {
		return User.builder()
				.id(userDTO.getId())
				.login(userDTO.getLogin())
				.password(userDTO.getPassword())
				.name(userDTO.getName())
				.phone(userDTO.getPhone())
				.email(userDTO.getEmail())
				.roles(RoleConverter.convertRoleIdsToEntity(userDTO.getRoleIds()))
				.build();
	}
	
	public static UserDTO convertUserToDTO(User user) {
		return UserDTO.builder()
				.id(user.getId())
				.login(user.getLogin())
				.name(user.getName())
				.phone(user.getPhone())
				.email(user.getEmail())
//				.roles(RoleConverter.convertFromSetRole(user.getRoles()))
				.build();
	}
	
	public static UserBaseDTO convertUserToBaseDTO(User user) {
		return UserBaseDTO.builder()
				.id(user.getId())
				.login(user.getLogin())
				.name(user.getName())
				.phone(user.getPhone())
				.email(user.getEmail())
				.build();
	}
	
}
