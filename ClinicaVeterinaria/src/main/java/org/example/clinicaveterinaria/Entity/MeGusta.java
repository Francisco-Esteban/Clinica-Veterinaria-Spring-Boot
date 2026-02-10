package org.example.clinicaveterinaria.Entity;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "noticiaId", "nombreUsuario" }))
public class MeGusta {

    // ESTRUCTURA DE LA CLASE MEGUSTA, QUE CORRESPONDE A UNA TABLA EN LA BD
    // GUARDA QUE USUARIO DIO LIKE A QUE NOTICIA

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long noticiaId;

    private String nombreUsuario;

    public MeGusta() {
    }

    public MeGusta(Long noticiaId, String nombreUsuario) {
        this.noticiaId = noticiaId;
        this.nombreUsuario = nombreUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoticiaId() {
        return noticiaId;
    }

    public void setNoticiaId(Long noticiaId) {
        this.noticiaId = noticiaId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
