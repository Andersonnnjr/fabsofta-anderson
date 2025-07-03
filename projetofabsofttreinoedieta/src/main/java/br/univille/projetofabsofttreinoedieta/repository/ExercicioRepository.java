package br.univille.projetofabsofttreinoedieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.univille.projetofabsofttreinoedieta.entity.Exercicio;

@Repository
public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {

}


