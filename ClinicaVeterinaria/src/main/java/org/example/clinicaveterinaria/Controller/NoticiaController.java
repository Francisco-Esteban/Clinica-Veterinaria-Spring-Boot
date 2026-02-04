package org.example.clinicaveterinaria.Controller;

import org.example.clinicaveterinaria.Repository.NoticiaRepositorio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoticiaController {

    private final NoticiaRepositorio noticiaRepositorio;

    // Inyección de dependencias por constructor (recomendada)
    public NoticiaController(NoticiaRepositorio noticiaRepositorio) {
        this.noticiaRepositorio = noticiaRepositorio;
    }

    @GetMapping("/noticias")
    public String listarNoticias(Model model) {

        // Obtenemos todas las noticias de la base de datos
        model.addAttribute("noticias", noticiaRepositorio.findAll());

        return "noticias"; // noticias.html
    }
}