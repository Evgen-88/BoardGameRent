package com.itrex.konoplyanik.boardgamerent.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.itrex.konoplyanik.boardgamerent.dto.UserAuthenticationDTO;
import com.itrex.konoplyanik.boardgamerent.exception.JwtAuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String secretKey;
	@Value("${jwt.expiration}")
	private long validityInMilliseconds;
	@Value("${jwt.issuer}")
	private String issuer;
	
	private final String TOKEN_PREFIX = "Bearer ";

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(UserAuthenticationDTO user) {
		return TOKEN_PREFIX + Jwts.builder()
				.setSubject(String.format("%s,%s", user.getId(), user.getUsername()))
				.setIssuer(issuer)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}

	public String removePrefixFromToken(String token) {
		return token.split(" ")[1];
	}

	public boolean validateToken(String token) throws JwtAuthenticationException {
		if (token.startsWith(TOKEN_PREFIX)) {
			try {
				token = removePrefixFromToken(token);
				Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
				return true;
			} catch (Exception ex) {
				log.error(ex.getMessage());
				throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
			}
		} else {
            throw new JwtAuthenticationException("Token sould start with Bearer");
        }
	}

	public String getUsername(String token) {
		
		 Claims claims = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
				
		 return claims.getSubject().split(",")[1];
	}

}
