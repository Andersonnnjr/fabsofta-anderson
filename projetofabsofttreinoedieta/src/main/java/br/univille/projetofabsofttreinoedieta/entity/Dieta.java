package br.univille.projetofabsofttreinoedieta.entity;

import java.util.List;

public class Dieta {
    private String nome;
    private String descricao;
    private List<String> alimentos;
    private int caloriasTotais;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getAlimentos() {
        return alimentos;
    }

    public void setAlimentos(List<String> alimentos) {
        this.alimentos = alimentos;
    }

    public int getCaloriasTotais() {
        return caloriasTotais;
    }

    public void setCaloriasTotais(int caloriasTotais) {
        this.caloriasTotais = caloriasTotais;
    }
}