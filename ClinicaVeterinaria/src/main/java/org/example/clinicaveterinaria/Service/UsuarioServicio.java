package org.example.clinicaveterinaria.Service;

import org.example.clinicaveterinaria.DTO.UsuarioRegistro;
import org.example.clinicaveterinaria.Entity.Usuario;
import org.example.clinicaveterinaria.Repository.UsuarioRepositorio;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    // Registrar un nuevo usuario (cliente)
    public Usuario registrarUsuario(UsuarioRegistro registroDTO) {

        // Crear el usuario con rol de CLIENTE por defecto
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(registroDTO.getNombreUsuario());
        nuevoUsuario.setEmail(registroDTO.getEmail());

        // Encriptar la contraseña antes de guardarla
        nuevoUsuario.setContraseña(passwordEncoder.encode(registroDTO.getContraseña()));

        nuevoUsuario.setRol("ROLE_CLIENTE");
        nuevoUsuario.setActivo(true);

        return usuarioRepositorio.save(nuevoUsuario);
    }

    // Verificar si el email ya existe
    public boolean existeEmail(String email) {
        return usuarioRepositorio.existsByEmail(email);
    }

    // Verificar si el nombre de usuario ya existe
    public boolean existeNombreUsuario(String nombreUsuario) {
        return usuarioRepositorio.existsByNombreUsuario(nombreUsuario);
    }

    // Guardar un usuario (útil para crear el admin inicial)
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }
}