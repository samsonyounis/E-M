package com.example.emapp.securityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class jwtUtility implements Serializable {

        private final String jwtSecret = "SECRET";
        //function to generate jwt token
        public String generateJwtToken(UserDetails userDetails) {
            Date expiryDate = new Date(System.currentTimeMillis() + 60000 * 15);

            Map<String, Object> claims = new HashMap<>();
            claims.put("roles",userDetails.getAuthorities());
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
        }
        //function to validate jwt token
        public Boolean validateJwtToken(String token, UserDetails userDetails) {
            String username = getUsernameFromToken(token);
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            boolean isTokenExpired = claims.getExpiration().before(new Date());
            return (username.equals(userDetails.getUsername()) && !isTokenExpired);
        }
        public String getUsernameFromToken(String token) {
            final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return claims.getSubject();
        }

}