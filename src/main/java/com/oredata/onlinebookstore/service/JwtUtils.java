package com.oredata.onlinebookstore.service;

import com.oredata.onlinebookstore.model.UserRole;
import com.oredata.onlinebookstore.model.dto.UserDTO;
import com.oredata.onlinebookstore.model.entity.UserEntity;
import com.oredata.onlinebookstore.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@RequiredArgsConstructor
@Component
public class JwtUtils {
    @Value("${app.jwt.secret}")
    private String secret;
    private final UserService userService;
    private final UserRepository userRepository;
    public Map<String, List<UserRole>> generateToken(UserDTO user) {
        UserEntity userByEmailAndPassword = userService.findUserByEmailAndPassword(user);
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        String jwt = Jwts.builder()
                .setSubject(userByEmailAndPassword.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 hour
                .claim("UserRole", userByEmailAndPassword.getRoles())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        Map<String, List<UserRole>> returnedMap = new HashMap<>();
        returnedMap.put(jwt, userByEmailAndPassword.getRoles());
        return returnedMap;
    }
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            System.out.println(claims);
            Date expiration = claims.getBody().getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        String email = getUsernameFromToken(token);
        UserEntity user = userRepository.findByEmail(email).get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(UserRole -> authorities.add(new SimpleGrantedAuthority(UserRole.name())));
        return new UsernamePasswordAuthenticationToken(user, "", authorities);
    }
}
