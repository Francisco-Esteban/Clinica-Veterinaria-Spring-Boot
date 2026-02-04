package org.example.clinicaveterinaria.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EncriptarContraseña {

    // ENCRIPTAR LA CONTRASEÑA, LA SUSTITUYE POR UNA CADENA DE CARACTERES

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}