package py.com.arquitickets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.arquitickets.models.Cliente;
import py.com.arquitickets.services.ClienteService;
import py.com.arquitickets.utils.Respuestas;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // GET: Obtener todas las categorías
    @GetMapping
    public ResponseEntity<Respuestas> getAllClientes() {
        List<Cliente> clientes = clienteService.getAllClientes();
        Respuestas response = new Respuestas(HttpStatus.OK, "Clientes Obtenidos", clientes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // GET: Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.getClienteById(id);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // PUT: Crear un nuevo cliente
    @PutMapping
    public ResponseEntity<Respuestas> createCategoria(@RequestBody Cliente cliente) {
        Cliente savedCliente = clienteService.saveCategoria(cliente);
        Respuestas response = new Respuestas(HttpStatus.OK, "Cliente creado exitosamente", savedCliente);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // POST: Actualizar un cliente existente
    @PostMapping("/{id}")
    public ResponseEntity<Respuestas> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Optional<Cliente> cliente = clienteService.getClienteById(id);
        if (cliente.isPresent()) {
            Cliente clienteToUpdate = cliente.get();
            clienteToUpdate.setNombre(clienteDetails.getNombre());
            clienteToUpdate.setApellido(clienteDetails.getApellido());
            clienteToUpdate.setNroDocumento(clienteDetails.getNroDocumento());
            clienteToUpdate.setEmail(clienteDetails.getEmail());
            clienteToUpdate.setTelefono(clienteDetails.getTelefono());
            clienteToUpdate.setFecNacimiento(clienteDetails.getFecNacimiento());
            clienteToUpdate.setCodTipDocumento(clienteDetails.getCodTipDocumento());
            clienteToUpdate.setCodPais(clienteDetails.getCodPais());
            clienteService.saveCategoria(clienteToUpdate);
            Respuestas response = new Respuestas(HttpStatus.OK, "Cliente Actualizado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Cliente no encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE: Eliminar una categoría por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Respuestas> deleteCliente(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.getClienteById(id);
        if (cliente.isPresent()) {
            clienteService.deleteCategoria(id);
            Respuestas response = new Respuestas(HttpStatus.OK, "Cliente eliminado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Respuestas response = new Respuestas(HttpStatus.OK, "Cliente no encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);        }
    }
}

