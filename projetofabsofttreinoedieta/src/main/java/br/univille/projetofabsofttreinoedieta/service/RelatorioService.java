package br.univille.projetofabsofttreinoedieta.service;

import java.util.List;
import br.univille.projetofabsofttreinoedieta.entity.Relatorio;

public interface RelatorioService {
    Relatorio save(Relatorio relatorio);
    List<Relatorio> getAll();
    Relatorio getById(Long id);
    Relatorio delete(Long id);
}

