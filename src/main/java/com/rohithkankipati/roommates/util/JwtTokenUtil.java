package com.rohithkankipati.roommates.util;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rohithkankipati.roommates.dto.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {

    @Autowired
    private SecretKeyService secretKeyService;

    private SecretKey getSecretKey() {
	try {
	    String secretKeyString = secretKeyService.getSecretKey();
	    return Keys.hmacShaKeyFor(secretKeyString.getBytes());
	} catch (IOException e) {
	    throw new IllegalStateException("Failed to load secret key", e);
	}
    }

    public String generateToken(UserDTO userDetails) {
	Map<String, Object> claims = new HashMap<>();
	claims.put("sub", userDetails.getUserName());
	claims.put("created", new Date());

	String rolesString = userDetails.getRoles().stream().map(Enum::name).collect(Collectors.joining(","));

	claims.put("roles", rolesString);

	return Jwts.builder().subject(userDetails.getUserName())
		.expiration(new Date(System.currentTimeMillis() + 604800 * 1000)).signWith(getSecretKey()).compact();

    }

    public boolean validateToken(String token, String username) {
	String tokenUsername = getUsernameFromToken(token);
	return (username.equals(tokenUsername) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token) {
	Jws<Claims> parsed = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token);
	return parsed.getPayload().getSubject();
    }

    private boolean isTokenExpired(String token) {
	Jws<Claims> parsed = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token);
	return parsed.getPayload().getExpiration().before(new Date());
    }
}
