package br.univille.projetofabsofttreinoedieta.service;

import java.util.List;
import br.univille.projetofabsofttreinoedieta.entity.Treino;

public interface TreinoService {
    Treino save(Treino treino);
    List<Treino> getAll();
    Treino getById(Long id);
    Treino delete(Long id);
}