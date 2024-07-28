package com.dc.springsecurityjwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMsl;


    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(UserDetails userDetails){

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authorities.stream().map(GrantedAuthority::getAuthority).toList());

        // Validate and sanitize audience if provided (optional)
     /*   String audience = userDetails.getA *//* Replace 'ge' with actual method *//*;
        if (audience != null && !audience.isEmpty()) {
            audience = sanitizeAudience(audience); // Implement custom logic to sanitize audience (if needed)
        }
*/

        System.out.println(userDetails.getUsername());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMsl))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean vlaidateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Example method to get username from JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token, jwtSecret);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token, String secret){
        SecretKey key = createSecretKey(secret);
        return Jwts
                .parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey createSecretKey(String secretKey){
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA512");
    }

    // Example method to check if token is expired
    public boolean isTokenExpired(String token) {
        final Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

   /* private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }*/
}
