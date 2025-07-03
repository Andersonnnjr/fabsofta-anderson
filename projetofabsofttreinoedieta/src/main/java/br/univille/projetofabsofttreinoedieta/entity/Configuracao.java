package br.univille.projetofabsofttreinoedieta.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Configuracao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String preferencias;
    private boolean notificacoes;
    private boolean lembretes;

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

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    public boolean isNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(boolean notificacoes) {
        this.notificacoes = notificacoes;
    }

    public boolean isLembretes() {
        return lembretes;
    }

    public void setLembretes(boolean lembretes) {
        this.lembretes = lembretes;
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

    public Object getChave() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getChave'");
    }

    public void setChave(Object chave) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setChave'");
    }

    public Object getValor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getValor'");
    }

    public void setValor(Object valor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setValor'");
    }
}
