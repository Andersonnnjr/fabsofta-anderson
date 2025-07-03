package br.univille.projetofabsofttreinoedieta.service;

import java.util.List;
import br.univille.projetofabsofttreinoedieta.entity.Exercicio;

public interface ExercicioService {
    Exercicio save(Exercicio exercicio);
    List<Exercicio> getAll();
    Exercicio getById(Long id);
    Exercicio delete(Long id);
}


