package br.univille.projetofabsofttreinoedieta.service;

import java.util.List;
import br.univille.projetofabsofttreinoedieta.entity.Progresso;

public interface ProgressoService {
    Progresso save(Progresso progresso);
    List<Progresso> getAll();
    Progresso getById(Long id);
    Progresso delete(Long id);
}


