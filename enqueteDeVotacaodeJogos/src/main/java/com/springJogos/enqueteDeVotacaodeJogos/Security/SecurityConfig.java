package com.springJogos.enqueteDeVotacaodeJogos.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

@Configuration
public class SecurityConfig {


    private final JwtFilter filter;

    @Autowired
    public SecurityConfig(JwtFilter filter) {
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(http -> http
                        .requestMatchers("/jogos", "/cadastro", "/login").permitAll()
                        .requestMatchers("/jogos/ranking").hasRole("ADMIN")
                        .anyRequest().authenticated()

                )
                .requiresChannel(channel -> channel.anyRequest().requiresSecure())
                .headers(header -> header.xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))

                        .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'; script-src 'self'; script-src-attr 'self'; style-src 'self'; img-src *; block-all-mixed-content"))
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessio -> sessio.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter , UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
