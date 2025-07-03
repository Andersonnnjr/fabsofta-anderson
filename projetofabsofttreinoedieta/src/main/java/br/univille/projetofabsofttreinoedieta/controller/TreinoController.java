package br.univille.projetofabsofttreinoedieta.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.univille.projetofabsofttreinoedieta.entity.Treino;
import br.univille.projetofabsofttreinoedieta.service.TreinoService;

@RestController
@RequestMapping("/api/v1/treinos")
public class TreinoController {

    @Autowired
    private TreinoService service;

    @GetMapping
    public ResponseEntity<List<Treino>> getTreinos() {
        var lista = service.getAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treino> getTreinoById(@PathVariable long id) {
        var obj = service.getById(id);
        if (obj == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Treino> postTreino(@RequestBody Treino obj) {
        if (obj == null) return ResponseEntity.badRequest().build();
        if (obj.getId() == 0) {
            service.save(obj);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Treino> putTreino(@PathVariable long id, @RequestBody Treino obj) {
        if (id <= 0 || obj == null) return ResponseEntity.badRequest().build();
        var antigo = service.getById(id);
        if (antigo == null) return ResponseEntity.notFound().build();
        antigo.setNome(obj.getNome());
        antigo.setDescricao(obj.getDescricao());
        antigo.setExercicios(obj.getExercicios());
        antigo.setDuracao(obj.getDuracao());
        antigo.setFrequencia(obj.getFrequencia());
        service.save(antigo);
        return new ResponseEntity<>(antigo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Treino> deleteTreino(@PathVariable long id) {
        if (id <= 0) return ResponseEntity.badRequest().build();
        var excluido = service.getById(id);
        if (excluido == null) return ResponseEntity.notFound().build();
        service.delete(id);
        return new ResponseEntity<>(excluido, HttpStatus.OK);
    }
}
