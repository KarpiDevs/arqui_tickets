package py.com.arquitickets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.arquitickets.models.Idioma;
import py.com.arquitickets.services.IdiomaService;
import py.com.arquitickets.utils.Respuestas;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/idiomas")
public class IdiomaController {

    private final IdiomaService idiomaService;

    @Autowired
    public IdiomaController(IdiomaService idiomaService) {
        this.idiomaService = idiomaService;
    }

    // GET: Obtener todas las categorías
    @GetMapping
    public ResponseEntity<Respuestas> getAllIdiomas() {
        List<Idioma> idiomas = idiomaService.getAllIdiomas();
        Respuestas response = new Respuestas(HttpStatus.OK, "Idiomas Obtenidos", idiomas);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // PUT: Crear nuevo idioma
    @PutMapping
    public ResponseEntity<Respuestas> createIdioma(@RequestBody Idioma idioma) {
            Idioma savedIdioma = idiomaService.saveIdioma(idioma);
        Respuestas response = new Respuestas(HttpStatus.OK, "Idioma creado exitosamente", savedIdioma);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{codIdioma}")
    public ResponseEntity<Respuestas> updateIdioma(@PathVariable String codIdioma, @RequestBody Idioma idiomaDetails) {
        Optional<Idioma> idioma = idiomaService.updateCodIdioma(codIdioma);
        if (idioma.isPresent()) {
            Idioma idiomaToUpdate = idioma.get();
            idiomaToUpdate.setActivo(idiomaDetails.getActivo());
            idiomaService.saveIdioma(idiomaToUpdate);
            Respuestas response = new Respuestas(HttpStatus.OK, "Idioma Actualizado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Idioma no encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}

