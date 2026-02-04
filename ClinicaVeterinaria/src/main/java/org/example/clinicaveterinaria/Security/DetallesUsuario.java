package org.example.clinicaveterinaria.Security;

import org.example.clinicaveterinaria.Entity.Usuario;
import org.example.clinicaveterinaria.Repository.UsuarioRepositorio;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class DetallesUsuario implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    public DetallesUsuario(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {

        // Buscar el usuario en la base de datos
        Usuario usuario = usuarioRepositorio.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + nombreUsuario));

        // Crear la autoridad (rol) del usuario
        GrantedAuthority autoridad = new SimpleGrantedAuthority(usuario.getRol());

        // Devolver un objeto UserDetails que Spring Security entiende
        return User.builder()
                .username(usuario.getNombreUsuario())
                .password(usuario.getContraseña())
                .authorities(Collections.singletonList(autoridad))
                .disabled(!usuario.isActivo())
                .build();
    }
}