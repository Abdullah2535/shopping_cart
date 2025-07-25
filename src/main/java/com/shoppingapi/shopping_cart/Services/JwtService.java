package com.shoppingapi.shopping_cart.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secret;
    final Long tokenExpiration = 86400L;
   public String generateToken(String email) {
      return Jwts.builder()
               .subject(email)
               .issuedAt(new Date())
               .expiration(new Date(System.currentTimeMillis() + tokenExpiration*1000))
               .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
               .compact();
   }
   public boolean validateToken(String token){
       try {
           var claims = getClaims(token);
           return    claims.getExpiration().after(new Date());
       }
       catch (JwtException ex) {
           return false;
       }
       }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getEmailFromToken(String token){
         return getClaims(token).getSubject();
       }


}
