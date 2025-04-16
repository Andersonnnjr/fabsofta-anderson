package br.univille.projetofabsofttreinoedieta.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alimentos")
public class AlimentoController {

    public ResponseEntity<List<Alimento>>getAlimento(){
        var listaAliemento = service.getAll();

        return new ResponseEntity<List<Alimento>>(listaAlimento, HttpStatus.OK);
    }
}
