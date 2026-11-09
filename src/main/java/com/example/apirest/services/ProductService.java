package com.example.apirest.services;
import com.example.apirest.controllers.dto.CrearProductRequest;
import com.example.apirest.controllers.dto.ProductResponse;
import com.example.apirest.persistence.entities.Product;
import io.swagger.v3.oas.annotations.servers.Server;

import java.util.List;


public interface ProductService {

    ProductResponse findByID(int id);

    List<Product> findAll();

    List<Product> findByCategoriaAndNameAndCodigo(String categoria, String name, String codigo);

    List<Product> findProductsPorQuery (String categoria, String name, Integer stockMinimo, Integer stockMaximo);

    ProductResponse save(CrearProductRequest product);

    ProductResponse update(CrearProductRequest product, Integer id);

    void delete (Integer id);
    }
