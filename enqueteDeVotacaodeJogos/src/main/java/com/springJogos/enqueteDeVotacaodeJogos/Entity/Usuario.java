package com.springJogos.enqueteDeVotacaodeJogos.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String usuario;
    @NotNull
    @Size(min = 8, message = "No mínimo 8 caracteres")
    private String senha;
    @Email(message = "Formato válido: exemploemail@hotmail.com")
    private String email;
    @NotNull
    private String role;

    public Usuario(){

    }

    public Usuario(Long id, String usuario, String senha, String email, String role) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



}
