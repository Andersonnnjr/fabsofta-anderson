package br.univille.projetofabsofttreinoedieta.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.time.LocalDate;

@Entity
public class Progresso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate data;
    private String treino;
    private String desempenho;
    private String observacoes;

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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTreino() {
        return treino;
    }

    public void setTreino(String treino) {
        this.treino = treino;
    }

    public String getDesempenho() {
        return desempenho;
    }

    public void setDesempenho(String desempenho) {
        this.desempenho = desempenho;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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

    public Object getPeso() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPeso'");
    }

    public void setPeso(Object peso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPeso'");
    }

    public Object getAltura() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAltura'");
    }

    public void setAltura(Object altura) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setAltura'");
    }
}
