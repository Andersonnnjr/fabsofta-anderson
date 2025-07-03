package br.univille.projetofabsofttreinoedieta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.univille.projetofabsofttreinoedieta.entity.Alimento;
import br.univille.projetofabsofttreinoedieta.service.AlimentoService;

@RestController
@RequestMapping("/api/v1/alimentos")
public class AlimentoController {

    @Autowired
    private AlimentoService service;

    @GetMapping
    public ResponseEntity<List<Alimento>> getAlimentos() {
        var listaAlimentos = service.getAll();
        return new ResponseEntity<List<Alimento>>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alimento> getAlimentoById(@PathVariable long id) {
        var alimento = service.getById(id);
        if (alimento == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<Alimento>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Alimento> postAlimento(@RequestBody Alimento alimento) {
        if (alimento == null) {
            return ResponseEntity.badRequest().build();
        }
        if (alimento.getId() == 0) {
            service.save(alimento);
            return new ResponseEntity<>(alimento, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alimento> putAlimento(@PathVariable long id, @RequestBody Alimento alimento) {
        if (id <= 0 || alimento == null) {
            return ResponseEntity.badRequest().build();
        }
        var alimentoAntigo = service.getById(id);
        if (alimentoAntigo == null) {
            return ResponseEntity.notFound().build();
        }
        alimento.setId(id);
        service.save(alimento);
        return new ResponseEntity<>(alimento, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlimento(@PathVariable long id) {
        var alimento = service.getById(id);
        if (alimento == null) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
