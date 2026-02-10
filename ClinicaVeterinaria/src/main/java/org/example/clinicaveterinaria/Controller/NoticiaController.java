package org.example.clinicaveterinaria.Controller;

import org.example.clinicaveterinaria.Entity.Noticia;
import org.example.clinicaveterinaria.Repository.NoticiaRepositorio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Controller
public class NoticiaController {

    private final NoticiaRepositorio noticiaRepositorio;

    public NoticiaController(NoticiaRepositorio noticiaRepositorio) {
        this.noticiaRepositorio = noticiaRepositorio;
    }

    // MOSTRAR TODAS LAS NOTICIAS GUARDADAS

    @GetMapping("/noticias")
    public String listarNoticias(Model model) {

        model.addAttribute("noticias", noticiaRepositorio.findAll());

        return "noticias"; // noticias.html
    }

    // CREAR UNA NUEVA

    @GetMapping("/nueva")
    public String nuevaNoticia(Model model) {
        model.addAttribute("noticia", new Noticia());
        return "agregar-noticia";
    }

    // EDITARLA

    @GetMapping("/editar/{id}")
    public String editarNoticia(@PathVariable Long id, Model model) {

        Noticia noticia = noticiaRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Noticia no encontrada"));

        model.addAttribute("noticia", noticia);
        return "agregar-noticia";
    }

    // BORRARLA

    @GetMapping("/borrar/{id}")
    public String borrarNoticia(@PathVariable Long id) {
        noticiaRepositorio.deleteById(id);
        return "redirect:/noticias";
    }

    // GUARDARLA

    @PostMapping("/guardar")
    public String guardarNoticia(
            @ModelAttribute Noticia noticia,
            @RequestParam(value = "archivoImagen1", required = false) MultipartFile img1,
            @RequestParam(value = "archivoImagen2", required = false) MultipartFile img2,
            @RequestParam(value = "archivoImagen3", required = false) MultipartFile img3,
            @RequestParam(value = "archivoImagen4", required = false) MultipartFile img4
    ) throws IOException {

        if (noticia.getFechaPublicacion() == null) {
            noticia.setFechaPublicacion(LocalDate.now());
        }

        guardarImagen(img1, noticia, 1);
        guardarImagen(img2, noticia, 2);
        guardarImagen(img3, noticia, 3);
        guardarImagen(img4, noticia, 4);

        noticiaRepositorio.save(noticia);
        return "redirect:/noticias";
    }

    // METODO AUXILIAR PARA GUARDARLA

    private void guardarImagen(MultipartFile archivo, Noticia noticia, int num)
            throws IOException {

        if (archivo != null && !archivo.isEmpty()) {

            String nombre = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
            Path ruta = Paths.get("src/main/resources/static/img/noticias/" + nombre);

            Files.createDirectories(ruta.getParent());
            Files.write(ruta, archivo.getBytes());

            switch (num) {
                case 1 -> noticia.setImagen1(nombre);
                case 2 -> noticia.setImagen2(nombre);
                case 3 -> noticia.setImagen3(nombre);
                case 4 -> noticia.setImagen4(nombre);
            }
        }
    }


}


