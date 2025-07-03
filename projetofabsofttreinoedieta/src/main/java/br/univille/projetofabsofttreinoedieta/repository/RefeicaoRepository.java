package br.univille.projetofabsofttreinoedieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.univille.projetofabsofttreinoedieta.entity.Refeicao;

@Repository
public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {

}
