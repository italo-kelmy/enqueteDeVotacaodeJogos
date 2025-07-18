package com.springJogos.enqueteDeVotacaodeJogos.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
public class Jogos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String ano;
    private String categoria;
    private String descricao;
    private int quantidadeVotada;


    public Jogos() {

    }

    public Jogos(Long id, String nome, String ano, String categoria, String descricao, int quantidadeVotada) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.categoria = categoria;
        this.descricao = descricao;
        this.quantidadeVotada = quantidadeVotada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidadeVotada() {
        return quantidadeVotada;
    }

    public void setQuantidadeVotada(int quantidadeVotada) {
        this.quantidadeVotada = quantidadeVotada;
    }


}
