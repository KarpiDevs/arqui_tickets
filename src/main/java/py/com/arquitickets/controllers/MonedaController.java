package py.com.arquitickets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.arquitickets.models.Empleado;
import py.com.arquitickets.models.Moneda;
import py.com.arquitickets.services.MonedaService;
import py.com.arquitickets.utils.Respuestas;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/monedas")
public class MonedaController {

    private final MonedaService monedaService;

    @Autowired
    public MonedaController(MonedaService monedaService) {
        this.monedaService = monedaService;
    }

    // GET: Obtener todas las categorías
    @GetMapping
    public ResponseEntity<Respuestas> getAllMonedas() {
        List<Moneda> monedas = monedaService.getAllMonedas();
        Respuestas response = new Respuestas(HttpStatus.OK, "Monedas Obtenidas", monedas);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // PUT: Crear una nueva moneda
    @PutMapping
    public ResponseEntity<Respuestas> createMoneda(@RequestBody Moneda moneda) {
            Moneda savedMoneda = monedaService.saveMoneda(moneda);
        Respuestas response = new Respuestas(HttpStatus.OK, "Moneda creada exitosamente", savedMoneda);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{codMoneda}")
    public ResponseEntity<Respuestas> updateMoneda(@PathVariable String codMoneda, @RequestBody Moneda monedaDetails) {
        Optional<Moneda> moneda = monedaService.updateCodMoneda(codMoneda);
        if (moneda.isPresent()) {
            Moneda monedaToUpdate = moneda.get();
            monedaToUpdate.setActivo(monedaDetails.getActivo());
            monedaService.saveMoneda(monedaToUpdate);
            Respuestas response = new Respuestas(HttpStatus.OK, "Moneda Actualizada");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Moneda no encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}

