package com.springJogos.enqueteDeVotacaodeJogos.Service;

import com.springJogos.enqueteDeVotacaodeJogos.Entity.Usuario;
import com.springJogos.enqueteDeVotacaodeJogos.Repository.UsuarioRepository;
import com.springJogos.enqueteDeVotacaodeJogos.Security.JwtUtil;
import com.springJogos.enqueteDeVotacaodeJogos.Security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository repository;
    private final SecurityConfig config;
    private final JwtUtil jwtUtil;

    @Autowired
    public UsuarioService(UsuarioRepository repository, SecurityConfig config, JwtUtil jwtUtil) {
        this.repository = repository;
        this.config = config;
        this.jwtUtil = jwtUtil;
    }



    public ResponseEntity<?> cadastro(Usuario usuario) {

        if (repository.findByUsuario(usuario.getUsuario()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já cadastrado");
        } else if (repository.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado");
        }

        usuario.setSenha(config.bCryptPasswordEncoder().encode(usuario.getSenha()));
        repository.save(usuario);
        return ResponseEntity.ok("Usuário cadastrado com sucesso");
    }

    public ResponseEntity<?> login(Usuario usuario) throws Exception {
        config.manager(new AuthenticationConfiguration()).authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsuario(), usuario.getSenha()));

        Usuario usuario1 = repository.findByUsuario(usuario.getUsuario()).orElseThrow(() -> new IllegalArgumentException("Usuário não cadastrado"));
        UserDetails user = User.builder()
                .username(usuario1.getUsuario())
                .password(usuario1.getSenha())
                .roles(usuario1.getRole())
                .build();
        return ResponseEntity.ok(jwtUtil.generateKey(user));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByUsuario(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));


        return User.builder()
                .username(usuario.getUsuario())
                .password(usuario.getSenha())
                .roles(usuario.getRole())
                .build();
    }
}
