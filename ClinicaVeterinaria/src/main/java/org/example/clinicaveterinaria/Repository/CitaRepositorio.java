package org.example.clinicaveterinaria.Repository;


import org.example.clinicaveterinaria.Entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface CitaRepositorio extends JpaRepository<Cita, Long> {

    boolean existsByFechaAndHora(LocalDate fecha, LocalTime hora);
}
