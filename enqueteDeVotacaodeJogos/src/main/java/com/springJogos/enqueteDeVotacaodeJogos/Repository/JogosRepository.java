package com.springJogos.enqueteDeVotacaodeJogos.Repository;

import com.springJogos.enqueteDeVotacaodeJogos.Entity.Jogos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface JogosRepository extends JpaRepository<Jogos, Long> {
    Optional<Jogos> findByNome(String nome);

    List<Jogos> findByCategoria(String categoria);

    List<Jogos> findAllByOrderByQuantidadeVotadaDesc();
}
