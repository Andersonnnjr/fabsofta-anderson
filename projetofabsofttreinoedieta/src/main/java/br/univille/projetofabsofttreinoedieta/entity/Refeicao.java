package br.univille.projetofabsofttreinoedieta.entity;

import java.time.LocalDate;
import java.util.List;

public class Refeicao {
    private LocalDate data;
    private List<String> alimentos;
    private int caloriasConsumidas;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<String> getAlimentos() {
        return alimentos;
    }

    public void setAlimentos(List<String> alimentos) {
        this.alimentos = alimentos;
    }

    public int getCaloriasConsumidas() {
        return caloriasConsumidas;
    }

    public void setCaloriasConsumidas(int caloriasConsumidas) {
        this.caloriasConsumidas = caloriasConsumidas;
    }
}