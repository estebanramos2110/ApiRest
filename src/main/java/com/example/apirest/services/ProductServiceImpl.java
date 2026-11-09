package com.example.apirest.services;

import com.example.apirest.controllers.dto.CrearProductRequest;
import com.example.apirest.controllers.dto.ProductResponse;
import com.example.apirest.persistence.entities.Product;
import com.example.apirest.persistence.repositories.ProductJpaRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepository productJpaRepository;

    public ProductServiceImpl(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public ProductResponse findByID(int id) {
        return productJpaRepository.findById(id).map(product -> new ProductResponse(
                product.getCategoria(),
                product.getName(),
                product.getPrecio(),
                product.getStock(),
                product.getCodigo()
        )).orElse(null);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }

    @Override
    public List<Product> findByCategoriaAndNameAndCodigo(String categoria, String name, String codigo) {
        return productJpaRepository.findByCategoriaAndNameAndCodigo(categoria,name,codigo);
    }

    @Override
    public List<Product> findProductsPorQuery(String categoria, String name, Integer stockMinimo, Integer stockMaximo) {
        return productJpaRepository.findProductPorQuery(categoria, name, stockMinimo, stockMaximo);
    }

    @Override
    public ProductResponse save(CrearProductRequest product) {

        if (productJpaRepository.findByName(product.getName()).isPresent()){
            throw new RuntimeException("producto con nombre " + product.getName() + " Ya existe" );
        }
        Product newProduct = new Product();
        newProduct.setActivo(product.isActivo());
        newProduct.setCategoria(product.getCategoria());
        newProduct.setName(product.getName());
        newProduct.setPrecio(product.getPrecio());
        newProduct.setStock(product.getStock());
        newProduct.setCodigo(product.getCodigo());

        Product savedProduct = productJpaRepository.save(newProduct);
        return new ProductResponse(
                savedProduct.getCategoria(),
                savedProduct.getName(),
                savedProduct.getPrecio(),
                savedProduct.getStock(),
                savedProduct.getCodigo()
        );
    }

    @Override
    public ProductResponse update(CrearProductRequest product, Integer id) {
        Product existingProduct = productJpaRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setActivo(product.isActivo());
            existingProduct.setCategoria(product.getCategoria());
            existingProduct.setName(product.getName());
            existingProduct.setPrecio(product.getPrecio());
            existingProduct.setStock(product.getStock());
            existingProduct.setCodigo(product.getCodigo());
            productJpaRepository.save(existingProduct);

            return new ProductResponse(
                    existingProduct.getCategoria(),
                    existingProduct.getName(),
                    existingProduct.getPrecio(),
                    existingProduct.getStock(),
                    existingProduct.getCodigo()
            );
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        if (!productJpaRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Producto con id " + id + " no existe");
        }
        productJpaRepository.deleteById(id);
    }
}
