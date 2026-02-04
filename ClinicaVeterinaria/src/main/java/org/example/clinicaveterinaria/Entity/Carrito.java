package org.example.clinicaveterinaria.Entity;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

@Component
@SessionScope

public class Carrito {

    private Map<Producto, Integer> productos = new HashMap<>();

    // OPERACIONES CON EL CARRITO

    public void agregarProducto(Producto producto) {

        productos.put(producto, productos.getOrDefault(producto, 0) + 1);
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

    public Map<Producto, Integer> getProductos() {
        return productos;
    }

    public double getTotal() {
        return productos.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrecio() * e.getValue())
                .sum();
    }

    public void vaciar() {
        productos.clear();
    }
}


