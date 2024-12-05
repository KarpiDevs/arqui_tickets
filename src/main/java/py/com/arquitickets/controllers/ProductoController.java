package py.com.arquitickets.controllers;

import py.com.arquitickets.dto.ProductoDTO;
import py.com.arquitickets.models.Categoria;
import py.com.arquitickets.models.Producto;
import py.com.arquitickets.services.CategoriaService;
import py.com.arquitickets.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.arquitickets.utils.Respuestas;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    @Autowired
    public ProductoController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    // GET: Obtener todos los productos
    @GetMapping
    public ResponseEntity<Respuestas> getAllProductos() {
        List<Producto> productos = productoService.getAllProductos();
        Respuestas response = new Respuestas(HttpStatus.OK, "Productos obtenidos exitosamente", productos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // GET: Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Optional<Producto> producto = productoService.getProductoById(id);
        return producto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST: Crear un nuevo producto
    @PutMapping
    public ResponseEntity<Respuestas> createProducto(@RequestBody ProductoDTO productoDTO) {
        // Buscar la categoría usando codCategoria
        Optional<Categoria> categoria = categoriaService.getCategoriaById(productoDTO.getCodCategoria());
        if (categoria.isPresent()) {
            // Crear y asignar datos al nuevo producto
            Producto producto = new Producto();

            producto.setDescProducto(productoDTO.getDescProducto());
            producto.setPrecioUnitario(productoDTO.getPrecioUnitario());
            producto.setStockActual(productoDTO.getStockActual());
            producto.setStockMinimo(productoDTO.getStockMinimo());
            producto.setCodImpuesto(productoDTO.getCodImpuesto());
            producto.setCategoria(categoria.get());

            Producto savedProducto = productoService.saveProducto(producto);
            Respuestas response = new Respuestas(HttpStatus.OK, "Producto creado exitosamente", savedProducto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Categoría no encontrada");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // PUT: Actualizar un producto existente
    @PostMapping("/{id}")
    public ResponseEntity<Respuestas> updateProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        Optional<Producto> producto = productoService.getProductoById(id);
        if (producto.isPresent()) {
            Optional<Categoria> categoria = categoriaService.getCategoriaById(productoDTO.getCodCategoria());
            if (categoria.isPresent()) {
                Producto productoToUpdate = producto.get();
                productoToUpdate.setDescProducto(productoDTO.getDescProducto());
                productoToUpdate.setPrecioUnitario(productoDTO.getPrecioUnitario());
                productoToUpdate.setCategoria(categoria.get());
                productoToUpdate.setStockActual(productoDTO.getStockActual());
                productoToUpdate.setStockMinimo(productoDTO.getStockMinimo());

                Producto updatedProducto = productoService.saveProducto(productoToUpdate);
                Respuestas response = new Respuestas(HttpStatus.OK, "Producto actualizado exitosamente", updatedProducto);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Producto no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE: Eliminar un producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Respuestas> deleteProducto(@PathVariable Long id) {
        Optional<Producto> producto = productoService.getProductoById(id);
        if (producto.isPresent()) {
            productoService.deleteProducto(id);
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Producto eliminado exitosamente");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Producto no encontrada");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}