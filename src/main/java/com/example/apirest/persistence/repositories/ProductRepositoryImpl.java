package com.example.apirest.persistence.repositories;

import com.example.apirest.persistence.entities.Product;
import java.util.List;
import org.springframework.stereotype.Component;


@Component

public class ProductRepositoryImpl {

    private final ProductJpaRepository productJpaRepository;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository){
        this.productJpaRepository = productJpaRepository;
    }

    public Product guardarProducto(Product product){
        return  productJpaRepository.save(product);
    }

    public  Product obtenerProductoPorId(Integer id) {
        return productJpaRepository.findById(id).orElse(null);
    }

    public Product updateProduct (Product product, Integer id) {

        Product existingProduct = productJpaRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setActivo(product.isActivo());
            existingProduct.setCategoria(product.getCategoria());
            existingProduct.setName(product.getName());
            existingProduct.setPrecio(product.getPrecio());
            existingProduct.setStock(product.getStock());
            existingProduct.setCodigo(product.getCodigo());
            return productJpaRepository.save(existingProduct);
        }

        return null;

    }

    public void deleteProduct(Integer id){
        productJpaRepository.deleteById(id);
    }

    public List<Product> findProductsPorQuery(String categoria, String name, Integer stockMinimo, Integer stockMaximo){
        return productJpaRepository.findProductPorQuery(categoria,name,stockMinimo,stockMaximo);
    }

    public List<Product> findByCategoriaAndNameAndCodigo(String categoria, String name, String codigo){
        return productJpaRepository.findByCategoriaAndNameAndCodigo(categoria,name,codigo);
    }

}
