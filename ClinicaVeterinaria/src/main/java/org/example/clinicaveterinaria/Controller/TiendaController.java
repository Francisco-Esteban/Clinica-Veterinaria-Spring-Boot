package org.example.clinicaveterinaria.Controller;

import org.example.clinicaveterinaria.Entity.Carrito;
import org.example.clinicaveterinaria.Entity.Producto;
import org.example.clinicaveterinaria.Repository.ProductoRepositorio;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@SessionAttributes("carrito")
@Controller
public class TiendaController {

    private final ProductoRepositorio productoRepositorio;

    public TiendaController(ProductoRepositorio productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

    // MOSTRAR TODOS LOS PRODUCTOS DE LA BD

    @GetMapping("/tienda")
    public String mostrarTienda(Model model) {
        model.addAttribute("productos", productoRepositorio.findAll());
        return "tienda";
    }

    // MOSTRAR PAGINA INDIVIDUAL PARA CADA PRODUCTO

    @GetMapping("/tienda/{id}")
    public String detalleProducto(@PathVariable Long id, Model model) {

        Optional<Producto> producto = productoRepositorio.findById(id);

        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "detalle-producto";
        }

        return "redirect:/tienda";
    }

    // EL ADMIN PUEDE CREAR, EDITAR Y BORRAR PRODUCTOS

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tienda/nuevo")
    public String mostrarFormularioProducto(Model model) {
        model.addAttribute("producto", new Producto());
        return "agregar-producto";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tienda/editar/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productoRepositorio.findById(id);

        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "agregar-producto";
        }

        return "redirect:/tienda";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/tienda/guardar")
    public String guardarProducto(
            @Valid Producto producto,
            BindingResult result) {

        if (result.hasErrors()) {
            return "agregar-producto";
        }

        productoRepositorio.save(producto);

        return "redirect:/tienda";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tienda/borrar/{id}")
    public String borrarProducto(@PathVariable Long id) {
        productoRepositorio.deleteById(id);
        return "redirect:/tienda";
    }

    @ModelAttribute("carrito")
    public Carrito carrito() {
        return new Carrito();
    }

    // CONSULTAR CARRITO

    @GetMapping("/carrito")
    public String verCarrito() {
        return "carrito";
    }

    // CARRITO DE COMPRA JUNTO A ID DE PRODUCTO AÑADIDO

    @PostMapping("/carrito/agregar/{id}")
    public String agregarAlCarrito(
            @PathVariable Long id,
            @ModelAttribute("carrito") Carrito carrito) {

        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        carrito.agregarProducto(producto);

        return "redirect:/tienda";
    }

    // CARRITO DE COMPRA JUNTO A ID DE PRODUCTO ELIMINADO

    @PostMapping("/carrito/eliminar/{id}")
    public String eliminarDelCarrito(
            @PathVariable Long id,
            @ModelAttribute("carrito") Carrito carrito) {

        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        carrito.eliminarProducto(producto);

        return "redirect:/carrito";
    }

    // VACIAR TODO EL CARRITO DE GOLPE

    @PostMapping("/carrito/vaciar")
    public String vaciarCarrito(@ModelAttribute("carrito") Carrito carrito) {
        carrito.vaciar();
        return "redirect:/carrito";
    }
}