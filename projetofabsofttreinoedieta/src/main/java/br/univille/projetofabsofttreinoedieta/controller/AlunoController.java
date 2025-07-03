package br.univille.projetofabsofttreinoedieta.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.univille.projetofabsofttreinoedieta.entity.Aluno;
import br.univille.projetofabsofttreinoedieta.service.AlunoService;

@RestController
@RequestMapping("/api/v1/alunos")
public class AlunoController {

    @Autowired
    private AlunoService service;

    @GetMapping
    public ResponseEntity<List<Aluno>> getAlunos() {
        var lista = service.getAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getAlunoById(@PathVariable long id) {
        var obj = service.getById(id);
        if (obj == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Aluno> postAluno(@RequestBody Aluno obj) {
        if (obj == null) return ResponseEntity.badRequest().build();
        if (obj.getIdade() == 0) {
            service.save(obj);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> putAluno(@PathVariable long id, @RequestBody Aluno obj) {
        if (id <= 0 || obj == null) return ResponseEntity.badRequest().build();
        var antigo = service.getById(id);
        if (antigo == null) return ResponseEntity.notFound().build();
        // Atualize os campos necessários aqui
        antigo.setNome(obj.getNome());
        antigo.setEmail(obj.getEmail());
        antigo.setDataNascimento(obj.getDataNascimento());
        antigo.setPeso(obj.getPeso());
        antigo.setAltura(obj.getAltura());
        // Adicione outros campos conforme sua entidade
        service.save(antigo);
        return new ResponseEntity<>(antigo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Aluno> deleteAluno(@PathVariable long id) {
        if (id <= 0) return ResponseEntity.badRequest().build();
        var excluido = service.getById(id);
        if (excluido == null) return ResponseEntity.notFound().build();
        service.delete(id);
        return new ResponseEntity<>(excluido, HttpStatus.OK);
    }
}
