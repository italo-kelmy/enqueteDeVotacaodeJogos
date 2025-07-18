package com.springJogos.enqueteDeVotacaodeJogos.Controller;

import com.springJogos.enqueteDeVotacaodeJogos.Entity.Usuario;
import com.springJogos.enqueteDeVotacaodeJogos.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {
    private final UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastroUsuario(@RequestBody @Valid Usuario usuario) {
        return service.cadastro(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) throws Exception {
        return service.login(usuario);
    }


}
