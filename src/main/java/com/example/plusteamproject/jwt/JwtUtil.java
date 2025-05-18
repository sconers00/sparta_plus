package com.example.plusteamproject.jwt;

import com.example.plusteamproject.domain.user.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String secretKey = "f7W!x9K@#b3R$y2UvZ6*MdQ!eK4^LpWs8GjN&ZuXtVqErJoBc1HsTmCz";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    public String generateToken(String username, Role role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            return null; //예외 커스텀 해줘야함 ! ! !
        }
    }

    public String extractEmail(String token) {
        Claims claims = validateToken(token);
        return claims.getSubject();
    }

    public String extractEmailFromClaims(Claims claims) {
        return claims.getSubject();
    }
}
