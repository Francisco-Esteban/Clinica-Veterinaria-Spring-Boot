package org.example.clinicaveterinaria.Repository;

import org.example.clinicaveterinaria.Entity.MeGusta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MeGustaRepositorio extends JpaRepository<MeGusta, Long> {

    // CONTAR CUANTOS LIKES TIENE UNA NOTICIA
    long countByNoticiaId(Long noticiaId);

    // SABER SI UN USUARIO YA DIO LIKE A UNA NOTICIA
    boolean existsByNoticiaIdAndNombreUsuario(Long noticiaId, String nombreUsuario);

    // QUITAR EL LIKE DE UN USUARIO A UNA NOTICIA
    @Transactional
    void deleteByNoticiaIdAndNombreUsuario(Long noticiaId, String nombreUsuario);
}
