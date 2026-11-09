package com.example.apirest.controllers;

import com.example.apirest.controllers.dto.CrearProductRequest;
import com.example.apirest.controllers.dto.ProductResponse;
import com.example.apirest.persistence.entities.Product;
import com.example.apirest.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "products", description = "Endpoint para administrar productos")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Usuario por id", description = "probando mi primer api")
    @ApiResponse(responseCode = "200", description = "Respuesta exitosa")
    public ResponseEntity<ProductResponse> findByid(@PathVariable Integer id) {
        ProductResponse product = productService.findByID(id);
        if (product != null){
            return ResponseEntity.ok(product);
        }else {
            return ResponseEntity.notFound().build();
        }

    }


    @GetMapping("/search")
    @Operation(summary = "Buscar productos con filtros", description = "Filtra por categoría, nombre y rango de stock")
    @ApiResponse(responseCode = "200", description = "Respuesta exitosa")
    public ResponseEntity<List<ProductResponse>> findByQuery(@RequestParam String categoria,
                                                             @RequestParam String name,
                                                             @RequestParam Integer stockMinimo,
                                                             @RequestParam Integer stockMaximo) {

        List<ProductResponse> products = productService
                .findProductsPorQuery(categoria, name, stockMinimo, stockMaximo)
                .stream()
                .map(p -> new ProductResponse(
                        p.getCategoria(),
                        p.getName(),
                        p.getPrecio(),
                        p.getStock(),
                        p.getCodigo()))
                .toList();

        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping
    @Operation(summary = "Todos los ususarios", description = "probando mi primer api")
    @ApiResponse(responseCode = "200", description = "Respuesta exitosa")
    public ResponseEntity<List<Product>> getProduct() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PutMapping
    @Operation(summary = "Actualizar productos", description = "Actualiza un producto")
    @ApiResponse(responseCode = "200", description = "Respuesta exitosa Producto actualizado")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Integer id,@RequestBody CrearProductRequest product) {
        return ResponseEntity.status(201).body(productService.update(product, id));
    }



    @PostMapping
    @Operation(summary = "Crear Producto", description = "Crea un nuevo Producto")
    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente")
    public ResponseEntity<ProductResponse> createProduct(CrearProductRequest product) {

        return ResponseEntity.status(201).body(productService.save(product));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto por su id")
    @ApiResponse(responseCode = "204", description = "Producto eliminado")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}