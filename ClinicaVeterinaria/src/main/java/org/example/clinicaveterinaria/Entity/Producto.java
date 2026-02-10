package org.example.clinicaveterinaria.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Objects;

@Entity
@Table(name = "productos")

public class Producto {

    // ESTRUCTURA DE LA CLASE PRODUCTO, QUE CORRESPONDE A UNA TABLA EN LA BD

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @NotBlank(message = "La descripcion no puede estar vacia")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private double precio;

    @NotNull(message = "El stock es obligatorio")
    @Positive(message = "El stock debe ser mayor que 0")
    private int stock;

    @NotBlank(message = "La imagen es obligatoria")
    private String imagen;

    private String categoria;

    public Producto() {
    }

    public Producto(String nombre, String descripcion, double precio, int stock, String imagen, String categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre; // ←
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // equals para controlar si ese producto ya ha sido añadido al carrito

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Producto producto = (Producto) o;
        return Objects.equals(id, producto.id);
    }

    // HashCode para saber donde guardar el nuevo producto que se acaba de añadir al
    // carrito

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
