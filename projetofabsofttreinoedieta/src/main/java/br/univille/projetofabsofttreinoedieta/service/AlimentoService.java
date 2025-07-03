package br.univille.projetofabsofttreinoedieta.service;

import java.util.List;
import br.univille.projetofabsofttreinoedieta.entity.Alimento;

public interface AlimentoService {
    Alimento save(Alimento alimento);
    List<Alimento> getAll();
    Alimento getById(Long id);
    Alimento delete(Long id);
}
