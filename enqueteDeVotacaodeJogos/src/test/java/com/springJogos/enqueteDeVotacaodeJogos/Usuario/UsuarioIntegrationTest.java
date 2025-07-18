package com.springJogos.enqueteDeVotacaodeJogos.Usuario;


import com.springJogos.enqueteDeVotacaodeJogos.Entity.Usuario;
import com.springJogos.enqueteDeVotacaodeJogos.Repository.UsuarioRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UsuarioRepository repository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @AfterEach
    void tearDown(){
        entityManager.clear();
    }

    @Test
    void testSalvarUsuario_DevePersistirNoBanco(){

        Usuario usuario = new Usuario();
        usuario.setUsuario("testuser");
        usuario.setSenha(passwordEncoder.encode("senha123"));
        usuario.setEmail("testemail@hotmail.com");
        usuario.setRole("USER");

        Usuario savedUsuario = repository.save(usuario);

        Assertions.assertNotNull(savedUsuario.getId());
        Assertions.assertEquals("testuser",savedUsuario.getUsuario());
        Assertions.assertTrue(passwordEncoder.matches("senha123", savedUsuario.getSenha()));
    }

    @Test
    void testBuscarPorUsuario_QuandoExistir_DeveRetornarUsuario(){

        Usuario usuario = new Usuario();
        usuario.setUsuario("existigunser");
        usuario.setSenha("senha123");
        usuario.setEmail("exemploemail@hotmail.com");
        usuario.setRole("USER");
        entityManager.persist(usuario);
        entityManager.flush();

        Optional<Usuario> buscarUsuario = repository.findByUsuario("existigunser");

        Assertions.assertTrue(buscarUsuario.isPresent());
        Assertions.assertEquals("existigunser",buscarUsuario.get().getUsuario());
    }

    @Test
    void testBuscarPorUsuario_QuandoNaoExistir_DeveRetornarVazio(){
        Optional<Usuario>buscarUsuario = repository.findByUsuario("NaoExiste");

        Assertions.assertFalse(buscarUsuario.isPresent());
    }


    @Test
    void testSalvarUsuarioComEmailInvalido_DeveFalhar() {
        Usuario usuario = new Usuario();
        usuario.setUsuario("test");
        usuario.setSenha("pass");
        usuario.setEmail("email-invalido");
        usuario.setRole("USER");

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(usuario);
        });
    }


}
