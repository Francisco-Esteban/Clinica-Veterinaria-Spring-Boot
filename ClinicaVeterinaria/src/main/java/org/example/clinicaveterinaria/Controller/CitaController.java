package org.example.clinicaveterinaria.Controller;


import org.example.clinicaveterinaria.Entity.Cita;
import org.example.clinicaveterinaria.Repository.CitaRepositorio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
public class CitaController {

    private final CitaRepositorio citaRepositorio;

    public CitaController(CitaRepositorio citaRepositorio) {
        this.citaRepositorio = citaRepositorio;
    }

    // FORMULARIO DE CITAS

    @GetMapping("/cita")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cita", new Cita());
        return "cita";
    }

    // GUARDAR LA CITA

    @PostMapping("/cita")
    public String guardarCita(@ModelAttribute("cita") Cita cita,
                              Principal principal,
                              Model model) {

        // Controlar si ese hueco ya ha sido reservado por otro usuario

        if (citaRepositorio.existsByFechaAndHora(cita.getFecha(), cita.getHora())) {
            model.addAttribute("error", "Hueco ya reservado. Por favor, elige otra fecha / hora");
            return "cita";
        }

        // Guardar reserva

        cita.setNombreUsuario(principal.getName());

        citaRepositorio.save(cita);
        model.addAttribute("cita", new Cita());
        model.addAttribute("mensaje", "¡Tu cita ha sido reservada!");

        return "cita";
    }

}
