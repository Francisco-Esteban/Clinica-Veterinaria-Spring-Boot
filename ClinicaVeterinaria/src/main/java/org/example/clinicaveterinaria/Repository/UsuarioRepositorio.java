package org.example.clinicaveterinaria.Repository;

import org.example.clinicaveterinaria.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    // Buscar usuario por nombre de usuario (usado por Spring Security)
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    // Verificar si existe un email (útil para evitar duplicados en registro)
    boolean existsByEmail(String email);

    // Verificar si existe un nombre de usuario (útil para evitar duplicados en registro)
    boolean existsByNombreUsuario(String nombreUsuario);
}
