package com.gametracker.backend.security;

import static java.time.temporal.ChronoUnit.DAYS;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
  @Value("${jwt.secret}")
  private String secretKey;

  public String issueToken(String subject) {
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(Instant.now().plus(7, DAYS)))
        .signWith(getSigningKey(), SignatureAlgorithm.HS512)
        .compact();
  }

  public String getSubject(String token) {
    return getClaims(token).getSubject();
  }

  private Claims getClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  public boolean isTokenValid(String token, String username) {
    String subject = getSubject(token);
    return subject.equals(username) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    Date today = Date.from(Instant.now());
    return getClaims(token).getExpiration().before(today);
  }
}
