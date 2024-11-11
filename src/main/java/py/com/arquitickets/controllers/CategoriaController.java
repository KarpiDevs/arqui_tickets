package py.com.arquitickets.controllers;

import py.com.arquitickets.models.Categoria;
import py.com.arquitickets.services.CategoriaService;
import py.com.arquitickets.utils.Respuestas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // GET: Obtener todas las categorías
    @GetMapping
    public ResponseEntity<Respuestas> getAllCategorias() {
        List<Categoria> categorias = categoriaService.getAllCategorias();
        Respuestas response = new Respuestas(HttpStatus.OK, "Categorías obtenidas exitosamente", categorias);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // GET: Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaService.getCategoriaById(id);
        return categoria.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // PUT: Crear una nueva categoría
    @PutMapping
    public ResponseEntity<Respuestas> createCategoria(@RequestBody Categoria categoria) {
        Categoria savedCategoria = categoriaService.saveCategoria(categoria);
        Respuestas response = new Respuestas(HttpStatus.OK, "Categoría creada exitosamente", savedCategoria);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // POST: Actualizar una categoría existente
    @PostMapping("/{id}")
    public ResponseEntity<Respuestas> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoriaDetails) {
        Optional<Categoria> categoria = categoriaService.getCategoriaById(id);
        if (categoria.isPresent()) {
            Categoria categoriaToUpdate = categoria.get();
            categoriaToUpdate.setDescCategoria(categoriaDetails.getDescCategoria());
            categoriaService.saveCategoria(categoriaToUpdate);
            Respuestas response = new Respuestas(HttpStatus.OK, "Categoría actualizada exitosamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Categoría no encontrada");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE: Eliminar una categoría por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Respuestas> deleteCategoria(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaService.getCategoriaById(id);
        if (categoria.isPresent()) {
            categoriaService.deleteCategoria(id);
            Respuestas response = new Respuestas(HttpStatus.OK, "Categoría eliminada correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Respuestas response = new Respuestas(HttpStatus.OK, "Categoría no encontrada");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);        }
    }
}

