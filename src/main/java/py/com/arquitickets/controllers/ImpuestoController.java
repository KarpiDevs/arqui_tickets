package py.com.arquitickets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.arquitickets.models.Impuesto;
import py.com.arquitickets.services.ImpuestoService;
import py.com.arquitickets.utils.Respuestas;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/impuestos")
public class ImpuestoController {

    private final ImpuestoService impuestoService;

    @Autowired
    public ImpuestoController(ImpuestoService impuestoService) {
        this.impuestoService = impuestoService;
    }

    // GET: Obtener todas las categorías
    @GetMapping
    public ResponseEntity<Respuestas> getAllImpuestos() {
        List<Impuesto> idiomas = impuestoService.getAllImpuestos();
        Respuestas response = new Respuestas(HttpStatus.OK, "Impuestos Obtenidos", idiomas);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // PUT: Crear nuevo idioma
    @PutMapping
    public ResponseEntity<Respuestas> createImpuesto(@RequestBody Impuesto impuesto) {
            Impuesto savedImpuesto = impuestoService.saveImpuesto(impuesto);
        Respuestas response = new Respuestas(HttpStatus.OK, "Impuesto creado exitosamente", savedImpuesto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Respuestas> updateImpuesto(@PathVariable Long id, @RequestBody Impuesto impuestoDetails) {
        Optional<Impuesto> impuesto = impuestoService.getImpuestoById(id);
        if (impuesto.isPresent()) {
            Impuesto impuestoToUpdate = impuesto.get();
            impuestoToUpdate.setActivo(impuestoDetails.getActivo());
            impuestoService.saveImpuesto(impuestoToUpdate);
            Respuestas response = new Respuestas(HttpStatus.OK, "Impuesto Actualizado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Impuesto no encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}

