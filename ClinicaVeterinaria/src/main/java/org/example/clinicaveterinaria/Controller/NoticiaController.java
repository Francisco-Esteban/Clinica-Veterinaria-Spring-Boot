package org.example.clinicaveterinaria.Controller;

import org.example.clinicaveterinaria.Entity.MeGusta;
import org.example.clinicaveterinaria.Entity.Noticia;
import org.example.clinicaveterinaria.Repository.MeGustaRepositorio;
import org.example.clinicaveterinaria.Repository.NoticiaRepositorio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class NoticiaController {

    private final NoticiaRepositorio noticiaRepositorio;
    private final MeGustaRepositorio meGustaRepositorio;

    public NoticiaController(NoticiaRepositorio noticiaRepositorio,
            MeGustaRepositorio meGustaRepositorio) {
        this.noticiaRepositorio = noticiaRepositorio;
        this.meGustaRepositorio = meGustaRepositorio;
    }

    // MOSTRAR TODAS LAS NOTICIAS GUARDADAS

    @GetMapping("/noticias")
    public String listarNoticias(Model model, Principal principal) {

        List<Noticia> noticias = noticiaRepositorio.findAll();
        model.addAttribute("noticias", noticias);

        // MAPA CON EL NUMERO DE LIKES DE CADA NOTICIA

        Map<Long, Long> likesConteo = new HashMap<>();
        for (Noticia noticia : noticias) {
            likesConteo.put(noticia.getId(), meGustaRepositorio.countByNoticiaId(noticia.getId()));
        }
        model.addAttribute("likesConteo", likesConteo);

        // SET CON LAS NOTICIAS QUE EL USUARIO ACTUAL YA DIO LIKE

        Set<Long> misLikes = new HashSet<>();
        if (principal != null) {
            for (Noticia noticia : noticias) {
                if (meGustaRepositorio.existsByNoticiaIdAndNombreUsuario(noticia.getId(), principal.getName())) {
                    misLikes.add(noticia.getId());
                }
            }
        }
        model.addAttribute("misLikes", misLikes);

        return "noticias"; // noticias.html
    }

    // DAR O QUITAR ME GUSTA A UNA NOTICIA

    @PostMapping("/noticia/{id}/megusta")
    public String toggleMeGusta(@PathVariable Long id, Principal principal) {

        String nombreUsuario = principal.getName();

        // SI YA DIO LIKE, SE LO QUITAMOS. SI NO, SE LO DAMOS.

        if (meGustaRepositorio.existsByNoticiaIdAndNombreUsuario(id, nombreUsuario)) {
            meGustaRepositorio.deleteByNoticiaIdAndNombreUsuario(id, nombreUsuario);
        } else {
            meGustaRepositorio.save(new MeGusta(id, nombreUsuario));
        }

        return "redirect:/noticias";
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
            @RequestParam(value = "archivoImagen4", required = false) MultipartFile img4) throws IOException {

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
