package com.itrex.konoplyanik.boardgamerent.dto;

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
public class UserUpdateDTO {

	private Long id;
	private String login;
	private String password;
	private String name;
	private Integer phone;
	private String email;
}
