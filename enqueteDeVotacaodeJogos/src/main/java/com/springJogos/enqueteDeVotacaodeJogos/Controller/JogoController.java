package com.springJogos.enqueteDeVotacaodeJogos.Controller;

import com.springJogos.enqueteDeVotacaodeJogos.Entity.Jogos;
import com.springJogos.enqueteDeVotacaodeJogos.Service.JogosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JogoController {
    private final JogosService service;

    @Autowired
    public JogoController(JogosService service) {
        this.service = service;
    }

    @GetMapping("/jogos")
    public List<Jogos> jogos() {
        return service.jogosList();
    }


    @GetMapping("/jogos/{nome}")
    public ResponseEntity<?> buscaPorNome(@PathVariable String nome) {
        return service.buscarPorNome(nome);
    }

    @GetMapping("/jogos/categoria")
    public ResponseEntity<?> buscarPorCategoria(@RequestParam String categoria) {
        return service.buscarPorCategoria(categoria);
    }

    @PostMapping("/jogos/votacao")
    public ResponseEntity<?> votacao(@RequestBody Jogos jogos) {
        return service.votacaoDeJogos(jogos);
    }

    @GetMapping("/jogos/ranking")
    public List<Jogos>rankingJogos(){
        return service.ranking();
    }

}
