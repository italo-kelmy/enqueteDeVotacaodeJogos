package com.springJogos.enqueteDeVotacaodeJogos;


import com.springJogos.enqueteDeVotacaodeJogos.Entity.Jogos;
import com.springJogos.enqueteDeVotacaodeJogos.Entity.Usuario;
import com.springJogos.enqueteDeVotacaodeJogos.Repository.JogosRepository;
import com.springJogos.enqueteDeVotacaodeJogos.Repository.UsuarioRepository;
import com.springJogos.enqueteDeVotacaodeJogos.Service.JogosService;
import com.springJogos.enqueteDeVotacaodeJogos.Service.UsuarioService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class SistemaCompletoIntegrationTest {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JogosService jogosService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JogosRepository jogosRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void testCadastroELogin_FluxoCompleto() throws Exception {

        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsuario("novouser");
        novoUsuario.setSenha("senha123");
        novoUsuario.setEmail("novo@example.com");
        novoUsuario.setRole("USER");

        ResponseEntity<?>respostaCadastro = usuarioService.cadastro(novoUsuario);

        Assertions.assertEquals(HttpStatus.OK, respostaCadastro.getStatusCode());

        Optional<Usuario>usuarioSalvo = usuarioRepository.findByUsuario("novouser");
        Assertions.assertTrue(usuarioSalvo.isPresent());
        Assertions.assertTrue(passwordEncoder.matches("senha123", usuarioSalvo.get().getSenha()));

        Usuario loginUsuario = new Usuario();
        loginUsuario.setUsuario("novouser");
        loginUsuario.setSenha("senha123");

        ResponseEntity<?>respostaLogin = usuarioService.login(loginUsuario);

        Assertions.assertEquals(HttpStatus.OK, respostaLogin.getStatusCode());
        Assertions.assertNotNull(respostaLogin.getBody());
    }

    @Test
    void testVotacao_FluxoCompleto(){
        Jogos jogo = new Jogos();
        jogo.setNome("Jogo Teste Votação");
        jogo.setAno("2023");
        jogo.setCategoria("Teste");
        jogo.setDescricao("Jogo para teste de votação");
        jogo.setQuantidadeVotada(0);

        jogosRepository.save(jogo);


        ResponseEntity<?>respostaBusca = jogosService.buscarPorNome("Jogo Teste Votação");
        Assertions.assertEquals(HttpStatus.OK, respostaBusca.getStatusCode());

        Assertions.assertNotNull(respostaBusca.getBody());
        jogo.setQuantidadeVotada(jogo.getQuantidadeVotada() + 1);
        jogosRepository.save(jogo);

        List<Jogos>ranking = jogosService.ranking();

        Assertions.assertFalse(ranking.isEmpty());
        Assertions.assertEquals("Jogo Teste Votação", ranking.get(0).getNome());
        Assertions.assertEquals(1, ranking.get(0).getQuantidadeVotada());
    }






}
