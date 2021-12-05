package com.itrex.konoplyanik.boardgamerent.dto;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.itrex.konoplyanik.boardgamerent.entity.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthenticationDTO implements UserDetails {
	
	private Long id;
	private String username;
    private String password;
    private Set<Role> roles;
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
                .map(role -> (GrantedAuthority) role::getName)
                .collect(Collectors.toSet());
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
