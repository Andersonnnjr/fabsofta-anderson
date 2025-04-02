package br.univille.projetofabsofttreinoedieta.entity;

public class Configuracao {
    private String preferencias;
    private boolean notificacoes;
    private boolean lembretes;

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
}