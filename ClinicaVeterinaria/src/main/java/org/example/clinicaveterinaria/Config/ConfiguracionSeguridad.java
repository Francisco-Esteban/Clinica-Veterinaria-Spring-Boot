package org.example.clinicaveterinaria.Config;

import org.example.clinicaveterinaria.Security.DetallesUsuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfiguracionSeguridad {

    private final DetallesUsuario detallesUsuario;
    private final PasswordEncoder passwordEncoder;

    public ConfiguracionSeguridad(DetallesUsuario detallesUsuario, PasswordEncoder passwordEncoder) {
        this.detallesUsuario = detallesUsuario;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth


                        .requestMatchers("/", "/home", "/noticias", "/tienda", "/tienda/{id}").permitAll()
                        .requestMatchers("/registro", "/login").permitAll()
                        .requestMatchers("/css/**", "/img/**", "/js/**").permitAll()


                        .requestMatchers("/cita", "/cita/**").hasAnyRole("CLIENTE", "ADMIN")
                        .requestMatchers("/carrito/**").hasAnyRole("CLIENTE", "ADMIN")


                        .requestMatchers("/tienda/nuevo", "/tienda/guardar").hasRole("ADMIN")
                        .requestMatchers("/tienda/editar/**", "/tienda/borrar/**").hasRole("ADMIN")


                        .requestMatchers("/nueva", "/guardar", "/editar/**", "/borrar/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(detallesUsuario);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}