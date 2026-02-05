package org.example.clinicaveterinaria.Init;

import org.example.clinicaveterinaria.Entity.Usuario;
import org.example.clinicaveterinaria.Repository.UsuarioRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    // CLASE QUE EJECUTA NADA MAS INICIAR EL PROGRAMA

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepositorio usuarioRepositorio,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // USUARIO ADMIN POR DEFECTO

        if (!usuarioRepositorio.existsByNombreUsuario("admin")) {

            Usuario admin = new Usuario();
            admin.setNombreUsuario("admin");
            admin.setEmail("admin@admin.com");
            admin.setContraseña(passwordEncoder.encode("admin"));

            admin.setRol("ROLE_ADMIN");
            admin.setActivo(true);

            usuarioRepositorio.save(admin);

            System.out.println("Usuario ADMIN creado por valores por defecto");
            System.out.println("Nombre: admin");
            System.out.println("Contraseña: admin");
        } else {
            System.out.println("Usuario ADMIN ya existe");
        }
    }
}