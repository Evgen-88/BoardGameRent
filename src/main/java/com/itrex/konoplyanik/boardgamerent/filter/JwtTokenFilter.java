package com.itrex.konoplyanik.boardgamerent.filter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.itrex.konoplyanik.boardgamerent.exception.JwtAuthenticationException;
import com.itrex.konoplyanik.boardgamerent.security.JwtTokenProvider;
import com.itrex.konoplyanik.boardgamerent.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;
	
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try{
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                token = jwtTokenProvider.removePrefixFromToken(token);
                UserDetails userDetails = userService.findByLogin(jwtTokenProvider.getUsername(token));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null,
                        Optional.ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(List.of())
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (JwtAuthenticationException ex) {
            log.error("InvalidJwtException {}\n", request.getRequestURI(), ex);
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            response.getWriter().write(ex.getMessage() + " Invalid token");
        }
    }


}
