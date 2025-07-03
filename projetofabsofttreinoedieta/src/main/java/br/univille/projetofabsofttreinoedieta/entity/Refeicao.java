package br.univille.projetofabsofttreinoedieta.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Refeicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate data;

    @ElementCollection
    private List<String> alimentos;

    private int caloriasConsumidas;

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

    public Object getNome() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNome'");
    }

    public void setNome(Object nome) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setNome'");
    }

    public Object getDescricao() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDescricao'");
    }

    public void setDescricao(Object descricao) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDescricao'");
    }

    public Object getHorario() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHorario'");
    }

    public void setHorario(Object horario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setHorario'");
    }
}
