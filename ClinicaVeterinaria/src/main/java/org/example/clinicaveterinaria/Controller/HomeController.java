package org.example.clinicaveterinaria.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // MOSTRAR LA PANTALLA DE INICIO

    @GetMapping("/") // http://localhost:8080/
    public String inicio() {

        return "home";

    }
}
