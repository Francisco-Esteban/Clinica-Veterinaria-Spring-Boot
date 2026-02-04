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

    // REGISTRAR USUARIO CON COMPROBACIONES DE NOMBRE Y EMAIL

    public Usuario registrarUsuario(UsuarioRegistro registroDTO) {


        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(registroDTO.getNombreUsuario());
        nuevoUsuario.setEmail(registroDTO.getEmail());


        nuevoUsuario.setContraseña(passwordEncoder.encode(registroDTO.getContraseña()));

        nuevoUsuario.setRol("ROLE_CLIENTE");
        nuevoUsuario.setActivo(true);

        return usuarioRepositorio.save(nuevoUsuario);
    }

    public boolean existeEmail(String email) {
        return usuarioRepositorio.existsByEmail(email);
    }

    public boolean existeNombreUsuario(String nombreUsuario) {
        return usuarioRepositorio.existsByNombreUsuario(nombreUsuario);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }
}