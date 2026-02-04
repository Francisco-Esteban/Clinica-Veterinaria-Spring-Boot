package org.example.clinicaveterinaria.Controller;


import org.example.clinicaveterinaria.Entity.Cita;
import org.example.clinicaveterinaria.Repository.CitaRepositorio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String guardarCita(@ModelAttribute("cita") Cita cita, Model model) {

        citaRepositorio.save(cita);
        model.addAttribute("cita", new Cita());
        model.addAttribute("mensaje", "¡Tu cita ha sido reservada!");

        return "cita";
    }

}
