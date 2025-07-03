package br.univille.projetofabsofttreinoedieta.service;

import java.util.List;
import br.univille.projetofabsofttreinoedieta.entity.Configuracao;

public interface ConfiguracaoService {
    Configuracao save(Configuracao configuracao);
    List<Configuracao> getAll();
    Configuracao getById(Long id);
    Configuracao delete(Long id);
}


