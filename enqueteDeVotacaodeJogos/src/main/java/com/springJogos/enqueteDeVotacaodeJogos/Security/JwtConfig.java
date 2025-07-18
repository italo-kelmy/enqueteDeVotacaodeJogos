package com.springJogos.enqueteDeVotacaodeJogos.Security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtConfig {

    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.validation}")
    private Long validation;

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public Long getValidation() {
        return validation;
    }
}
