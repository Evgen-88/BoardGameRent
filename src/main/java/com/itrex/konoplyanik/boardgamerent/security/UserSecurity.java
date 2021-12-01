package com.itrex.konoplyanik.boardgamerent.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.itrex.konoplyanik.boardgamerent.entity.User;

import lombok.Data;

@Data
public class UserSecurity implements UserDetails {

    private final String login;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;

    public UserSecurity(String login, String password, List<SimpleGrantedAuthority> authorities) {
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
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

    public static UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
        		user.getLogin(), user.getPassword(), user.getRoles().stream()
        		.map((m) -> new SimpleGrantedAuthority(m.getName()))
        		.collect(Collectors.toList())
        );
    }
}
