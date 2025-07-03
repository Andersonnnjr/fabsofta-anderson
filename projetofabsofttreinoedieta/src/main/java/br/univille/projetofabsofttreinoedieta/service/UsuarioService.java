package br.univille.projetofabsofttreinoedieta.service;

import java.util.List;
import br.univille.projetofabsofttreinoedieta.entity.Usuario;

public interface UsuarioService {
    Usuario save(Usuario usuario);
    List<Usuario> getAll();
    Usuario getById(Long id);
    Usuario delete(Long id);
}

