package com.springJogos.enqueteDeVotacaodeJogos.Usuario;

import com.springJogos.enqueteDeVotacaodeJogos.Entity.Usuario;
import com.springJogos.enqueteDeVotacaodeJogos.Repository.UsuarioRepository;
import com.springJogos.enqueteDeVotacaodeJogos.Security.JwtUtil;
import com.springJogos.enqueteDeVotacaodeJogos.Security.SecurityConfig;
import com.springJogos.enqueteDeVotacaodeJogos.Service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.Optional;


public class UsuarioTest {

    @Mock
    private UsuarioRepository repository;
    @Mock
    private SecurityConfig config;
    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UsuarioService service;


    @BeforeEach
    void testUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(config.bCryptPasswordEncoder()).thenReturn(new BCryptPasswordEncoder());
    }

    @Test
    void testUsuario_Cadastro_Com_Sucesso() {

        Usuario usuario = new Usuario(1L, "italokelmy", "senha123", "kelmy54@hotmail.com", "USER");

        usuario.setSenha(config.bCryptPasswordEncoder().encode("senha123"));
        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<?> resposta = service.cadastro(usuario);
        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertEquals("Usuário cadastrado com sucesso", resposta.getBody());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Usuario.class));
    }

    @Test
    void testUsuario_Erro_Quando_Usuario_Ja_Cadastrado() {
        String usuario1 = "italokelmy";
        Usuario usuario = new Usuario(1L, usuario1, "senha123", "kelmy54@hotmail.com", "USER");

        Mockito.when(repository.findByUsuario(usuario1)).thenReturn(Optional.of(usuario));


        ResponseEntity<?> resposta = service.cadastro(usuario);
        Assertions.assertEquals(HttpStatus.CONFLICT, resposta.getStatusCode());
        Assertions.assertEquals("Usuário já cadastrado", resposta.getBody());
        Mockito.verify(repository, Mockito.times(1)).findByUsuario(usuario1);
    }


    @Test
    void testUsuario_Erro_Quando_Email_Ja_Cadastrado() {
        String email = "kelmy54@hotmail.com";
        Usuario usuario = new Usuario(1L, "italokelmy", "senha123", email, "USER");

        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));


        ResponseEntity<?> resposta = service.cadastro(usuario);

        Assertions.assertEquals(HttpStatus.CONFLICT, resposta.getStatusCode());
        Assertions.assertEquals("Email já cadastrado", resposta.getBody());
        Mockito.verify(repository, Mockito.times(1)).findByEmail(email);
    }

    @Test
    void testUsuario_RetoranrToken_Ao_Fazer_Login_Na_Conta() throws Exception {
        String usuario = "italokelmy";
        String senha = "senha123";
        String role = "USER";

        Usuario login = new Usuario();
        login.setUsuario(usuario);
        login.setSenha(senha);
        login.setRole(role);

        AuthenticationManager manager = Mockito.mock(AuthenticationManager.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(config.manager(Mockito.any())).thenReturn(manager);
        Mockito.when(manager.authenticate(Mockito.any())).thenReturn(authentication);

        Mockito.when(repository.findByUsuario(usuario)).thenReturn(Optional.of(login));
        Mockito.when(jwtUtil.generateKey(Mockito.any())).thenReturn("token-teste");

        ResponseEntity<?> resposta = service.login(login);
        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertEquals("token-teste", resposta.getBody());
    }


}
