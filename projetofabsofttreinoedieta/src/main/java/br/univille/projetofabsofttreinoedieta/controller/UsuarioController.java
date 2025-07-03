package br.univille.projetofabsofttreinoedieta.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.univille.projetofabsofttreinoedieta.entity.Usuario;
import br.univille.projetofabsofttreinoedieta.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios() {
        var lista = service.getAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable long id) {
        var obj = service.getById(id);
        if (obj == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Usuario> postUsuario(@RequestBody Usuario obj) {
        if (obj == null) return ResponseEntity.badRequest().build();
        if (obj.getId() == 0) {
            service.save(obj);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> putUsuario(@PathVariable long id, @RequestBody Usuario obj) {
        if (id <= 0 || obj == null) return ResponseEntity.badRequest().build();
        var antigo = service.getById(id);
        if (antigo == null) return ResponseEntity.notFound().build();
        // Atualize os campos necessários aqui
        antigo.setNome(obj.getNome());
        antigo.setEmail(obj.getEmail());
        antigo.setSenha(obj.getSenha());
        antigo.setTipo(obj.getTipo());
        service.save(antigo);
        return new ResponseEntity<>(antigo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deleteUsuario(@PathVariable long id) {
        if (id <= 0) return ResponseEntity.badRequest().build();
        var excluido = service.getById(id);
        if (excluido == null) return ResponseEntity.notFound().build();
        service.delete(id);
        return new ResponseEntity<>(excluido, HttpStatus.OK);
    }
}
