package com.academia.model;

public class Treino {

    private Integer id;
    private String nome;
    private String descricao;

    public Treino() {
    }

    public Treino(
            String nome,
            String descricao
    ) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(
            String descricao
    ) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return nome;
    }
}