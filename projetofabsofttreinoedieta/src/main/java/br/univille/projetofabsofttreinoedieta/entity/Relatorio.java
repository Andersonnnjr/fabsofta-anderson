package br.univille.projetofabsofttreinoedieta.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Relatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tipo;
    private String periodo;
    private String dados;
    private String conclusoes;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getDados() {
        return dados;
    }

    public void setDados(String dados) {
        this.dados = dados;
    }

    public String getConclusoes() {
        return conclusoes;
    }

    public void setConclusoes(String conclusoes) {
        this.conclusoes = conclusoes;
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
}
