package br.univille.projetofabsofttreinoedieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.univille.projetofabsofttreinoedieta.entity.Dieta;

@Repository
public interface DietaRepository extends JpaRepository<Dieta, Long> {

}


