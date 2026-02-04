package org.example.clinicaveterinaria.Config;

import org.example.clinicaveterinaria.Security.DetallesUsuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SeguridadConfig {

    private final DetallesUsuario detallesUsuario;
    private final PasswordEncoder passwordEncoder;

    public SeguridadConfig(DetallesUsuario detallesUsuario, PasswordEncoder passwordEncoder) {
        this.detallesUsuario = detallesUsuario;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // ========== RUTAS PÚBLICAS (sin autenticación) ==========
                        .requestMatchers("/", "/home", "/noticias", "/tienda", "/tienda/{id}").permitAll()
                        .requestMatchers("/registro", "/login").permitAll()
                        .requestMatchers("/css/**", "/img/**", "/js/**").permitAll()

                        // ========== RUTAS PARA CLIENTES (requieren login) ==========
                        .requestMatchers("/cita", "/cita/**").hasAnyRole("CLIENTE", "ADMIN")
                        .requestMatchers("/carrito/**").hasAnyRole("CLIENTE", "ADMIN")

                        // ========== RUTAS SOLO PARA ADMIN ==========
                        .requestMatchers("/tienda/nuevo", "/tienda/guardar").hasRole("ADMIN")
                        .requestMatchers("/tienda/editar/**", "/tienda/borrar/**").hasRole("ADMIN")

                        // ========== CUALQUIER OTRA RUTA REQUIERE AUTENTICACIÓN ==========
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")              // Página de login personalizada
                        .defaultSuccessUrl("/", true)      // Redirigir a home después de login
                        .failureUrl("/login?error=true")   // Si falla el login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")              // URL para cerrar sesión
                        .logoutSuccessUrl("/")             // Redirigir a home después de logout
                        .invalidateHttpSession(true)       // Invalidar la sesión
                        .deleteCookies("JSESSIONID")       // Borrar cookies
                        .permitAll()
                );

        return http.build();
    }

    /*

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.DetallesUsuario(detallesUsuario);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

     */
}
