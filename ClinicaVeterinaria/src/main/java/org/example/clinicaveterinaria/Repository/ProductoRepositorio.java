package org.example.clinicaveterinaria.Repository;

import org.example.clinicaveterinaria.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

    // BUSCAR PRODUCTOS POR CATEGORIA
    java.util.List<Producto> findByCategoria(String categoria);
}
