package com.springJogos.enqueteDeVotacaodeJogos.Jogos;

import com.springJogos.enqueteDeVotacaodeJogos.Entity.Jogos;
import com.springJogos.enqueteDeVotacaodeJogos.Repository.JogosRepository;
import com.springJogos.enqueteDeVotacaodeJogos.Service.JogosService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JogosTest {

    @Mock
    private JogosRepository repository;
    @InjectMocks
    private JogosService service;

    @BeforeEach
    void testUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testJogos_Retornar_Jogos_Disponivel_Na_Lista() {
        Jogos jogo1 = new Jogos(1L, "Cyberpunk 2077", "2020", "RPG", "Futurista, mundo aberto, com narrativa intensa e personalização.", 2);
        Jogos jogo2 = new Jogos(2L, "Red Dead Redemption 2", "2018", "Ação/Aventura", "Exploração do Velho Oeste com narrativa rica e mundo aberto", 20);

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(jogo1, jogo2));

        List<Jogos> resposta = service.jogosList();
        Assertions.assertEquals(2, resposta.size());

    }


    @Test
    void testJogos_Buscar_Jogo_PeloNome_Deve_Retornar_O_Jogo_Escolhido() {

        String nome = "Cyberpunk 2077";
        Jogos jogo = new Jogos(1L, nome, "2020", "RPG", "Futurista, mundo aberto, com narrativa intensa e personalização.", 2);
        Mockito.when(repository.findByNome(nome)).thenReturn(Optional.of(jogo));

        ResponseEntity<?> resposta = service.buscarPorNome(nome);
        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertNotNull(resposta.getBody());
    }

    @Test
    void testJogos_retornar_Erro_Quando_Jogo_Nao_Existir() {
        String nome = "naoExiste";
        Mockito.when(repository.findByNome(nome)).thenReturn(Optional.empty());

        ResponseEntity<?> resposta = service.buscarPorNome(nome);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
        Assertions.assertEquals("Jogo não encontrado", resposta.getBody());
        Mockito.verify(repository, Mockito.times(1)).findByNome(nome);
    }

    @Test
    void testJogos_retornar_A_Categoria_Dos_Jogos_Disponivel() {
        String categoria = "Ação/Aventura";
        Jogos jogo = new Jogos(2L, "Red Dead Redemption 2", "2018", categoria, "Exploração do Velho Oeste com narrativa rica e mundo aberto", 20);

        Mockito.when(repository.findByCategoria(categoria)).thenReturn(Collections.singletonList(jogo));

        ResponseEntity<?> resposta = service.buscarPorCategoria(categoria);
        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertNotNull(resposta.getBody());
    }

    @Test
    void testJogos_Erro_Quando_Categoria_Nao_Existir() {
        String categoria = "Não existe";
        Mockito.when(repository.findByCategoria(categoria)).thenReturn(Collections.emptyList());

        ResponseEntity<?> resposta = service.buscarPorCategoria(categoria);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
        Assertions.assertEquals("Categoria não disponível", resposta.getBody());
        Mockito.verify(repository, Mockito.times(1)).findByCategoria(categoria);
    }


    @Test
    void testJogos_Retornar_Jogos_Mais_Votados() {
        Jogos jogo1 = new Jogos(1L, "Cyberpunk 2077", "2020", "RPG", "Futurista, mundo aberto, com narrativa intensa e personalização.", 2);
        Jogos jogo2 = new Jogos(2L, "Red Dead Redemption 2", "2018", "Ação/Aventura", "Exploração do Velho Oeste com narrativa rica e mundo aberto", 20);

        Mockito.when(repository.findAllByOrderByQuantidadeVotadaDesc()).thenReturn(Arrays.asList(jogo1, jogo2));

        List<Jogos> resposta = service.ranking();
        Assertions.assertEquals(2, resposta.size());
        Mockito.verify(repository, Mockito.times(1)).findAllByOrderByQuantidadeVotadaDesc();
    }


}
