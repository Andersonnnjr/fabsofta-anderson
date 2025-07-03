package br.univille.projetofabsofttreinoedieta.service;

import java.util.List;
import br.univille.projetofabsofttreinoedieta.entity.Dieta;

public interface DietaService {
    Dieta save(Dieta dieta);
    List<Dieta> getAll();
    Dieta getById(Long id);
    Dieta delete(Long id);
}

