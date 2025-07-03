package br.univille.projetofabsofttreinoedieta.service;

import java.util.List;
import br.univille.projetofabsofttreinoedieta.entity.Aluno;

public interface AlunoService {
    Aluno save(Aluno aluno);
    List<Aluno> getAll();
    Aluno getById(Long id);
    Aluno delete(Long id);
}
