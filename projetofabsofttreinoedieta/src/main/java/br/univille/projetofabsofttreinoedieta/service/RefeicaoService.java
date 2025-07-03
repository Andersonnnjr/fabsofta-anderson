package br.univille.projetofabsofttreinoedieta.service;

import java.util.List;
import br.univille.projetofabsofttreinoedieta.entity.Refeicao;

public interface RefeicaoService {
    Refeicao save(Refeicao refeicao);
    List<Refeicao> getAll();
    Refeicao getById(Long id);
    Refeicao delete(Long id);
}


