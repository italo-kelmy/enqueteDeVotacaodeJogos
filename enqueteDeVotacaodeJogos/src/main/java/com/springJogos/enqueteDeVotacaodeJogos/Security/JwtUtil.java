package com.springJogos.enqueteDeVotacaodeJogos.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final JwtConfig config;

    @Autowired
    public JwtUtil(JwtConfig config) {
        this.config = config;
    }

    public String generateKey(UserDetails userDetails) {
        return Jwts.builder()
                .signWith(config.secretKey())
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + config.getValidation()))
                .compact();
    }

    public Claims extrairAllClaims(String key){
        return Jwts.parser()
                .verifyWith(config.secretKey())
                .build()
                .parseSignedClaims(key)
                .getPayload();
    }

    public <T> T extrairClaims(String key, Function<Claims, T>resolver){
        return resolver.apply(extrairAllClaims(key));
    }

    public boolean validarClaims(String key, UserDetails userDetails){
        String usuario = extrairClaims(key, Claims::getSubject);
        return usuario.equals(userDetails.getUsername()) && isValidator(key);
    }

    private boolean isValidator(String key) {
        return extrairClaims(key, Claims::getExpiration).after(new Date());
    }


}
