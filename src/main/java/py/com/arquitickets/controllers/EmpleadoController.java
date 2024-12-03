package py.com.arquitickets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.arquitickets.models.Empleado;
import py.com.arquitickets.services.EmpleadoService;
import py.com.arquitickets.utils.Respuestas;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    // GET: Obtener todos los empleados
    @GetMapping
    public ResponseEntity<Respuestas> getAllEmpleados() {
        List<Empleado> empleados = empleadoService.getAllEmpleados();
        Respuestas response = new Respuestas(HttpStatus.OK, "Empleados Obtenidos", empleados);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // GET: Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Long id) {
        Optional<Empleado> empleado = empleadoService.getEmpleadoById(id);
        return empleado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // PUT: Crear un nuevo empleado
    @PutMapping
    public ResponseEntity<Respuestas> createEmpleado(@RequestBody Empleado empleado) {
        Empleado savedEmpleado = empleadoService.saveEmpleado(empleado);
        Respuestas response = new Respuestas(HttpStatus.OK, "Empleado creado exitosamente", savedEmpleado);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // POST: Actualizar un empleado existente
    @PostMapping("/{id}")
    public ResponseEntity<Respuestas> updateEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoDetails) {
        Optional<Empleado> empleado = empleadoService.getEmpleadoById(id);
        if (empleado.isPresent()) {
            Empleado empleadoToUpdate = empleado.get();
            empleadoToUpdate.setNombre(empleadoDetails.getNombre());
            empleadoToUpdate.setApellido(empleadoDetails.getApellido());
            empleadoToUpdate.setNroDocumento(empleadoDetails.getNroDocumento());
            empleadoToUpdate.setEmail(empleadoDetails.getEmail());
            empleadoToUpdate.setTelefono(empleadoDetails.getTelefono());
            empleadoToUpdate.setFecNacimiento(empleadoDetails.getFecNacimiento());
            empleadoToUpdate.setCodTipDocumento(empleadoDetails.getCodTipDocumento());
            empleadoToUpdate.setCodPais(empleadoDetails.getCodPais());
            empleadoService.saveEmpleado(empleadoToUpdate);
            Respuestas response = new Respuestas(HttpStatus.OK, "Empleado Actualizado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Empleado no encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE: Eliminar una categoría por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Respuestas> deleteEmpleado(@PathVariable Long id) {
        Optional<Empleado> cliente = empleadoService.getEmpleadoById(id);
        if (cliente.isPresent()) {
            empleadoService.deleteEmpleado(id);
            Respuestas response = new Respuestas(HttpStatus.OK, "Empleado eliminado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Respuestas response = new Respuestas(HttpStatus.OK, "Empleado no encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);        }
    }
}

