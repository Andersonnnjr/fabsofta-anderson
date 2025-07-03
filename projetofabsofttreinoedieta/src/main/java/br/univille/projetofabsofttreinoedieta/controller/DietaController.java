package br.univille.projetofabsofttreinoedieta.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.univille.projetofabsofttreinoedieta.entity.Dieta;
import br.univille.projetofabsofttreinoedieta.service.DietaService;

@RestController
@RequestMapping("/api/v1/dietas")
public class DietaController {

    @Autowired
    private DietaService service;

    @GetMapping
    public ResponseEntity<List<Dieta>> getDietas() {
        var lista = service.getAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dieta> getDietaById(@PathVariable long id) {
        var obj = service.getById(id);
        if (obj == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Dieta> postDieta(@RequestBody Dieta obj) {
        if (obj == null) return ResponseEntity.badRequest().build();
        if (obj.getId() == 0) {
            service.save(obj);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dieta> putDieta(@PathVariable long id, @RequestBody Dieta obj) {
        if (id <= 0 || obj == null) return ResponseEntity.badRequest().build();
        var antigo = service.getById(id);
        if (antigo == null) return ResponseEntity.notFound().build();
        // Atualize os campos necessários aqui
        antigo.setNome(obj.getNome());
        antigo.setDescricao(obj.getDescricao());
        antigo.setRefeicoes(obj.getRefeicoes());
        // Adicione outros campos conforme sua entidade
        service.save(antigo);
        return new ResponseEntity<>(antigo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Dieta> deleteDieta(@PathVariable long id) {
        if (id <= 0) return ResponseEntity.badRequest().build();
        var excluido = service.getById(id);
        if (excluido == null) return ResponseEntity.notFound().build();
        service.delete(id);
        return new ResponseEntity<>(excluido, HttpStatus.OK);
    }
}
