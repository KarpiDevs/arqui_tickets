package py.com.arquitickets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.arquitickets.models.Pais;
import py.com.arquitickets.services.PaisService;
import py.com.arquitickets.utils.Respuestas;

import java.util.List;

@RestController
@RequestMapping("/api/paises")
public class PaisController {

    private final PaisService paisService;

    @Autowired
    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    // GET: Obtener todas las categorías
    @GetMapping
    public ResponseEntity<Respuestas> getAllPaises() {
        List<Pais> paises = paisService.getAllPaises();
        Respuestas response = new Respuestas(HttpStatus.OK, "Paises Obtenidos", paises);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

