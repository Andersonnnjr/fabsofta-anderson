package br.univille.projetofabsofttreinoedieta.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.univille.projetofabsofttreinoedieta.entity.Progresso;
import br.univille.projetofabsofttreinoedieta.service.ProgressoService;

@RestController
@RequestMapping("/api/v1/progressos")
public class ProgressoController {

    @Autowired
    private ProgressoService service;

    @GetMapping
    public ResponseEntity<List<Progresso>> getProgressos() {
        var lista = service.getAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Progresso> getProgressoById(@PathVariable long id) {
        var obj = service.getById(id);
        if (obj == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Progresso> postProgresso(@RequestBody Progresso obj) {
        if (obj == null) return ResponseEntity.badRequest().build();
        if (obj.getId() == 0) {
            service.save(obj);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Progresso> putProgresso(@PathVariable long id, @RequestBody Progresso obj) {
        if (id <= 0 || obj == null) return ResponseEntity.badRequest().build();
        var antigo = service.getById(id);
        if (antigo == null) return ResponseEntity.notFound().build();
        // Atualize os campos necessários aqui
        antigo.setPeso(obj.getPeso());
        antigo.setAltura(obj.getAltura());
        antigo.setData(obj.getData());
        // Adicione outros campos conforme sua entidade
        service.save(antigo);
        return new ResponseEntity<>(antigo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Progresso> deleteProgresso(@PathVariable long id) {
        if (id <= 0) return ResponseEntity.badRequest().build();
        var excluido = service.getById(id);
        if (excluido == null) return ResponseEntity.notFound().build();
        service.delete(id);
        return new ResponseEntity<>(excluido, HttpStatus.OK);
    }
}
