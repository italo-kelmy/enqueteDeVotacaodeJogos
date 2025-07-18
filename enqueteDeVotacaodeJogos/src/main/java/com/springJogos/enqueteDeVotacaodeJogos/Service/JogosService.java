package com.springJogos.enqueteDeVotacaodeJogos.Service;

import com.springJogos.enqueteDeVotacaodeJogos.Entity.Jogos;
import com.springJogos.enqueteDeVotacaodeJogos.Repository.JogosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class JogosService {

    private final JogosRepository repository;

    @Autowired
    public JogosService(JogosRepository repository) {
        this.repository = repository;
    }

    public List<Jogos> jogosList() {
        return repository.findAll();
    }


    public ResponseEntity<?> buscarPorNome(String nome) {


        if (repository.findByNome(nome).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jogo não encontrado");
        }

        return ResponseEntity.ok(repository.findByNome(nome));
    }


    public ResponseEntity<?> buscarPorCategoria(String categoria) {

        if (repository.findByCategoria(categoria).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não disponível");
        }

        return ResponseEntity.ok(repository.findByCategoria(categoria));
    }

    public ResponseEntity<?> votacaoDeJogos(Jogos id) {
        Jogos jogos = repository.findById(id.getId()).orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado"));

        jogos.setQuantidadeVotada(jogos.getQuantidadeVotada() + 1);
        repository.save(jogos);
        return ResponseEntity.ok("Jogo votado com sucesso");
    }

    public List<Jogos> ranking() {
        return repository.findAllByOrderByQuantidadeVotadaDesc();
    }

}
