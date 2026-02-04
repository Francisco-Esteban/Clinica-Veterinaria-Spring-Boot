package org.example.clinicaveterinaria.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity // Indica que esta clase es una tabla en la base de datos
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clave primaria

    private String titulo;

    @Column(length = 1000)
    private String contenido;

    private LocalDate fechaPublicacion;

    // Constructor vacío obligatorio para JPA
    public Noticia() {}

    // Getters y setters (necesarios para JPA)

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}