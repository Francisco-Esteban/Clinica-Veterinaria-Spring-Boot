package org.example.clinicaveterinaria.Controller;

import jakarta.validation.Valid;
import org.example.clinicaveterinaria.DTO.UsuarioRegistro;
import org.example.clinicaveterinaria.Service.UsuarioServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AutenticacionController {

    private final UsuarioServicio usuarioServicio;

    public AutenticacionController(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    // FORMULARIO DE LOGIN

    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               Model model) {

        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }

        if (logout != null) {
            model.addAttribute("mensaje", "Has cerrado sesión correctamente");
        }

        return "login";
    }

    // FORMULARIO DE REGISTRO

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new UsuarioRegistro());
        return "registro";
    }

    // COMPROBAR REGISTRO

    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") UsuarioRegistro registroDTO,
                                   BindingResult result,
                                   Model model) {

        // ERRORES DE VERIFICACION

        if (result.hasErrors()) {
            return "registro";
        }

        // LOS DOS CAMPOS DE LA CONTRASEÑA COINCIDEN

        if (!registroDTO.getContraseña().equals(registroDTO.getConfirmarContraseña())) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "registro";
        }

        // EL MAIL NO YA ESTA EN LA BD

        if (usuarioServicio.existeEmail(registroDTO.getEmail())) {
            model.addAttribute("error", "El email ya está registrado");
            return "registro";
        }

        // EL NOMBRE DE USUARIO YA ESTA EN LA BD

        if (usuarioServicio.existeNombreUsuario(registroDTO.getNombreUsuario())) {
            model.addAttribute("error", "El nombre de usuario ya está existe");
            return "registro";
        }

        // GUARDAR USUARIO SI TODO ES CORRECTO

        usuarioServicio.registrarUsuario(registroDTO);

        // CONFIRMACION

        model.addAttribute("mensaje", "¡Todo perfecto! Ya puedes iniciar sesión");
        return "redirect:/login?registroExitoso";
    }
}
