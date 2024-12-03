package py.com.arquitickets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.arquitickets.dto.PropinaDTO;
import py.com.arquitickets.models.Propina;
import py.com.arquitickets.services.PropinaService;
import py.com.arquitickets.utils.Respuestas;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/propinas")
public class PropinaController {

    private final PropinaService propinaService;

    @Autowired
    public PropinaController(PropinaService propinaService) {
        this.propinaService = propinaService;
    }

    // Obtener todas las propinas
    @GetMapping
    public ResponseEntity<Respuestas> getAllPropinas() {
        List<Propina> propinas = propinaService.getAllPropinas();
        Respuestas response = new Respuestas(HttpStatus.OK, "Propinas obtenidas exitosamente", propinas);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Obtener una propina por ID
    @GetMapping("/{id}")
    public ResponseEntity<Respuestas> getPropinaById(@PathVariable Long id) {
        Optional<Propina> propina = propinaService.getPropinaById(id);
        if (propina.isPresent()) {
            Respuestas response = new Respuestas(HttpStatus.OK, "Propina encontrada", propina.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Propina no encontrada");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Obtener propinas por fecha
    @GetMapping("/fecha")
    public ResponseEntity<Respuestas> getPropinasByFecha(@RequestParam("fecha") String fecha) {
        try {
            // Parsear la fecha desde el parámetro recibido
            LocalDate fechaParsed = LocalDate.parse(fecha);
            List<Propina> propinas = propinaService.getPropinasByFecha(fechaParsed);
            Respuestas response = new Respuestas(HttpStatus.OK, "Propinas encontradas", propinas);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Respuestas response = new Respuestas(HttpStatus.BAD_REQUEST, "Fecha inválida");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
