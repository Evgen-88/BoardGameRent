package com.itrex.konoplyanik.boardgamerent.converters;

import com.itrex.konoplyanik.boardgamerent.dto.UserBaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserSaveDTO;
import com.itrex.konoplyanik.boardgamerent.entity.User;

public class UserConverter {

	public static User convertUserToEntity(UserSaveDTO userDTO) {
		User user = new User();
		user.setId(userDTO.getId());
		user.setLogin(userDTO.getLogin());
		user.setPassword(userDTO.getPassword());
		user.setName(userDTO.getName());
		user.setPhone(userDTO.getPhone());
		user.setEmail(userDTO.getEmail());
		user.setRoles(RoleConverter.convertRolesToEntity(userDTO.getRoles()));
		return user;
	}
	
	public static UserDTO convertUserToDTO(User user) {
		return UserDTO.builder()
				.id(user.getId())
				.login(user.getLogin())
				.name(user.getName())
				.phone(user.getPhone())
				.email(user.getEmail())
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
