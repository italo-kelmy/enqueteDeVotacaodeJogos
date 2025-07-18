package com.springJogos.enqueteDeVotacaodeJogos.Jogos;

import com.springJogos.enqueteDeVotacaodeJogos.Entity.Jogos;
import com.springJogos.enqueteDeVotacaodeJogos.Repository.JogosRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JogosIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private JogosRepository repository;

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    void testSalvarJogos_DevePersistirNoBanco() {
        Jogos jogo = new Jogos();
        jogo.setNome("The Witcher 3");
        jogo.setAno("2015");
        jogo.setCategoria("RPG");
        jogo.setDescricao("Jogo de RPG fantástico");
        jogo.setQuantidadeVotada(100);

        Jogos savedJogo = repository.save(jogo);
        Assertions.assertNotNull(savedJogo.getId());
        Assertions.assertEquals("The Witcher 3", savedJogo.getNome());
        Assertions.assertEquals(100, savedJogo.getQuantidadeVotada());
    }


    @Test
    void testBuscarPorNome_QuandoExistir_DeveRetornarJogo() {

        Jogos jogo = new Jogos();
        jogo.setNome("Cyberpunk 2077");
        jogo.setAno("2020");
        entityManager.persist(jogo);
        entityManager.flush();

        Optional<Jogos> buscarJogo = repository.findByNome("Cyberpunk 2077");

        Assertions.assertTrue(buscarJogo.isPresent());
        Assertions.assertEquals("2020", buscarJogo.get().getAno());
    }

    @Test
    void testBuscarPorNome_QuandoNaoExistir_DeveRetornarVazio(){
        Optional<Jogos>jogoNaoExistente = repository.findByNome("Nome Inexistente");

        Assertions.assertFalse(jogoNaoExistente.isPresent());
    }

    @Test
    void testBuscarPorCategoria_QuandoNaoExistir_DeveRetornarListaVazia(){
        List<Jogos>jogosNaoExistente = repository.findByCategoria("Categoria Inexsistente");

        Assertions.assertTrue(jogosNaoExistente.isEmpty());
    }



    @Test
    void testBuscarPorCategoria_DeveRetornarListaCorreta(){
        // Jogo RPG 1
        Jogos jogo1 = new Jogos();
        jogo1.setNome("Jogo RPG 1");
        jogo1.setCategoria("RPG");
        entityManager.persist(jogo1);

        // Jogo RPG 2
        Jogos jogo2 = new Jogos();
        jogo2.setNome("Jogo RPG 2");
        jogo2.setCategoria("RPG");
        entityManager.persist(jogo2);

        // Jogo de Ação (outra categoria)
        Jogos jogo3 = new Jogos();
        jogo3.setNome("Jogo Ação");
        jogo3.setCategoria("Ação");
        entityManager.persist(jogo3);
        entityManager.flush();

        List<Jogos>rpgs = repository.findByCategoria("RPG");

        Assertions.assertEquals(2, rpgs.size());
        Assertions.assertTrue(rpgs.stream().allMatch(j -> j.getCategoria().equals("RPG")));

    }

    @Test
    void testRankingPorVotos_DeveRetornarEmOrdemDecrescente(){
        Jogos jogo1 = new Jogos();
        jogo1.setNome("Jogo Poucos Votos");
        jogo1.setQuantidadeVotada(10);
        entityManager.persist(jogo1);

        // 1º lugar
        Jogos jogo2 = new Jogos();
        jogo2.setNome("Jogo Muitos Votos");
        jogo2.setQuantidadeVotada(50);
        entityManager.persist(jogo2);

        // 2º lugar
        Jogos jogo3 = new Jogos();
        jogo3.setNome("Jogo Votos Medianos");
        jogo3.setQuantidadeVotada(30);
        entityManager.persist(jogo3);
        entityManager.flush();

        List<Jogos> ranking = repository.findAllByOrderByQuantidadeVotadaDesc();

        Assertions.assertEquals(3, ranking.size());
        Assertions.assertEquals("Jogo Muitos Votos", ranking.get(0).getNome());

        Assertions.assertEquals("Jogo Votos Medianos", ranking.get(1).getNome());
        Assertions.assertEquals(30, ranking.get(1).getQuantidadeVotada());

        Assertions.assertEquals("Jogo Poucos Votos", ranking.get(2).getNome());
        Assertions.assertEquals(10, ranking.get(2).getQuantidadeVotada());


    }




}
