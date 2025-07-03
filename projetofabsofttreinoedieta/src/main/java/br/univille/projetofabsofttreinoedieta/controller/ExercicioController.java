package br.univille.projetofabsofttreinoedieta.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.univille.projetofabsofttreinoedieta.entity.Exercicio;
import br.univille.projetofabsofttreinoedieta.service.ExercicioService;

@RestController
@RequestMapping("/api/v1/exercicios")
public class ExercicioController {

    @Autowired
    private ExercicioService service;

    @GetMapping
    public ResponseEntity<List<Exercicio>> getExercicios() {
        var lista = service.getAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercicio> getExercicioById(@PathVariable long id) {
        var obj = service.getById(id);
        if (obj == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Exercicio> postExercicio(@RequestBody Exercicio obj) {
        if (obj == null) return ResponseEntity.badRequest().build();
        if (obj.getId() == 0) {
            service.save(obj);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercicio> putExercicio(@PathVariable long id, @RequestBody Exercicio obj) {
        if (id <= 0 || obj == null) return ResponseEntity.badRequest().build();
        var antigo = service.getById(id);
        if (antigo == null) return ResponseEntity.notFound().build();
        // Atualize os campos necessários aqui
        antigo.setNome(obj.getNome());
        antigo.setDescricao(obj.getDescricao());
        antigo.setDescricao(obj.getDescricao());
        antigo.setIntensidade(obj.getIntensidade());
        // Adicione outros campos conforme sua entidade
        service.save(antigo);
        return new ResponseEntity<>(antigo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Exercicio> deleteExercicio(@PathVariable long id) {
        if (id <= 0) return ResponseEntity.badRequest().build();
        var excluido = service.getById(id);
        if (excluido == null) return ResponseEntity.notFound().build();
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
