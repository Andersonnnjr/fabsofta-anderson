package br.univille.projetofabsofttreinoedieta.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Exercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private String descricao;
    private String grupoMuscular;
    private int caloriasQueimadas;

    private String arquivoFoto;
    private String mimeType;

    @Transient
    private String foto;

    // Getters e Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public int getCaloriasQueimadas() {
        return caloriasQueimadas;
    }

    public void setCaloriasQueimadas(int caloriasQueimadas) {
        this.caloriasQueimadas = caloriasQueimadas;
    }

    public String getArquivoFoto() {
        return arquivoFoto;
    }

    public void setArquivoFoto(String arquivoFoto) {
        this.arquivoFoto = arquivoFoto;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Object getIntensidade() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIntensidade'");
    }

    public void setIntensidade(Object intensidade) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setIntensidade'");
    }
}
