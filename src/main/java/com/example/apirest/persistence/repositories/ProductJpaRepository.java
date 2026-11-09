package com.example.apirest.persistence.repositories;

import com.example.apirest.persistence.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);

    List<Product> findByCategoriaAndNameAndCodigo(String categoria, String name, String codigo);

    @Query("SELECT p FROM Product p WHERE " +
            "p.categoria = :categoria AND " +
            "p.name = :name AND " +
            "p.stock > :stockMinimo AND p.stock < :stockMaximo")
    List<Product> findProductPorQuery(@Param("categoria") String categoria,
                                      @Param("name") String name,
                                      @Param("stockMinimo") Integer stockMinimo,
                                      @Param("stockMaximo") Integer stockMaximo);

}