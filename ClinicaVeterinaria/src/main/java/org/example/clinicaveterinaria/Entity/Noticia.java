package org.example.clinicaveterinaria.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Noticia {

    // ESTRUCTURA DE LA CLASE NOTICIA, QUE CORRESPONDE A UNA TABLA EN LA BD

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(length = 1000)
    private String contenido;

    private LocalDate fechaPublicacion;

    private String imagen1;
    private String imagen2;
    private String imagen3;
    private String imagen4;

    public Noticia() {}

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

    public String getImagen1() {
        return imagen1;
    }

    public String getImagen2() {
        return imagen2;
    }

    public String getImagen3() {
        return imagen3;
    }

    public String getImagen4() {
        return imagen4;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setImagen1(String imagen1) {
        this.imagen1 = imagen1;
    }

    public void setImagen2(String imagen2) {
        this.imagen2 = imagen2;
    }

    public void setImagen3(String imagen3) {
        this.imagen3 = imagen3;
    }

    public void setImagen4(String imagen4) {
        this.imagen4 = imagen4;
    }
}