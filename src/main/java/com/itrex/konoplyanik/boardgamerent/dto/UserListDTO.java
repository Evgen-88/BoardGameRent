package com.itrex.konoplyanik.boardgamerent.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class UserListDTO {

	private Long id;
	private String login;
	private String name;
	private Integer phone;
	private String email;
	private List<RoleDTO> roles;
}
