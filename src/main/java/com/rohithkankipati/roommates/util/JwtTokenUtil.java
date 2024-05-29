package com.rohithkankipati.roommates.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rohithkankipati.roommates.dto.UserDTO;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;

@Component
public class JwtTokenUtil {

    @Autowired
    private SecretKeyService secretKeyService;

    private String getSecretKey() {
        try {
            return secretKeyService.getSecretKey();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load secret key", e);
        }
    }

    
    public String generateToken(UserDTO userDetails) {
      Map<String, Object> claims = new HashMap<>();
      claims.put("sub", userDetails.getUserName());
      claims.put("created", new Date());

      String rolesString = userDetails.getRoles().stream()
                                      .map(Enum::name)
                                      .collect(Collectors.joining(","));

      claims.put("roles", rolesString);

      return Jwts.builder()
          .setClaims(claims)
          .setExpiration(new Date(System.currentTimeMillis() + 604800 * 1000))  // 1 week
          .signWith(SignatureAlgorithm.HS512, getSecretKey())
          .compact();
  }


    public boolean validateToken(String token, String username) {
        String tokenUsername = getUsernameFromToken(token);
        return (username.equals(tokenUsername) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(getSecretKey())
                            .parseClaimsJws(token)
                            .getBody();
        return claims.getSubject();
    }

    private boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(getSecretKey())
                            .parseClaimsJws(token)
                            .getBody();
        return claims.getExpiration().before(new Date());
    }
}
