package br.univille.projetofabsofttreinoedieta.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.univille.projetofabsofttreinoedieta.entity.Relatorio;
import br.univille.projetofabsofttreinoedieta.service.RelatorioService;

@RestController
@RequestMapping("/api/v1/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService service;

    @GetMapping
    public ResponseEntity<List<Relatorio>> getRelatorios() {
        var lista = service.getAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Relatorio> getRelatorioById(@PathVariable long id) {
        var obj = service.getById(id);
        if (obj == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Relatorio> postRelatorio(@RequestBody Relatorio obj) {
        if (obj == null) return ResponseEntity.badRequest().build();
        if (obj.getId() == 0) {
            service.save(obj);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Relatorio> putRelatorio(@PathVariable long id, @RequestBody Relatorio obj) {
        if (id <= 0 || obj == null) return ResponseEntity.badRequest().build();
        var relatorioExistente = service.getById(id);
        if (relatorioExistente == null) return ResponseEntity.notFound().build();
        obj.setId(id);
        service.save(obj);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelatorio(@PathVariable long id) {
        var obj = service.getById(id);
        if (obj == null) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
