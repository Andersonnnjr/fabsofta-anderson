package br.univille.projetofabsofttreinoedieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.univille.projetofabsofttreinoedieta.entity.Progresso;

@Repository
public interface ProgressoRepository extends JpaRepository<Progresso, Long> {

}
