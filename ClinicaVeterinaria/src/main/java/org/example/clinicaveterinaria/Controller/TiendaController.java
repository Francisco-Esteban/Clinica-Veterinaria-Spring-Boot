package org.example.clinicaveterinaria.Controller;

import org.example.clinicaveterinaria.Entity.Carrito;
import org.example.clinicaveterinaria.Entity.Producto;
import org.example.clinicaveterinaria.Repository.ProductoRepositorio;
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

    @GetMapping("/tienda")
    public String mostrarTienda(Model model) {

        model.addAttribute("productos", productoRepositorio.findAll());

        return "tienda";
    }

    @GetMapping("/tienda/{id}")
    public String detalleProducto(@PathVariable Long id, Model model) {

        // Buscamos el producto por su id
        Optional<Producto> producto = productoRepositorio.findById(id);

        // Si existe, lo enviamos a la vista
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "detalle-producto";
        }

        // Si no existe, volvemos a la tienda
        return "redirect:/tienda";
    }

    @GetMapping("/tienda/nuevo")
    public String mostrarFormularioProducto(Model model) {

        model.addAttribute("producto", new Producto());

        return "agregar-producto";
    }

    @GetMapping("/tienda/editar/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {

        Optional<Producto> producto = productoRepositorio.findById(id);

        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "agregar-producto";
        }

        return "redirect:/tienda";
    }

    @PostMapping("/tienda/guardar")
    public String guardarProducto(
            @Valid Producto producto,
            BindingResult result) {

        // Si hay errores, volvemos al formulario
        if (result.hasErrors()) {
            return "agregar-producto";
        }

        productoRepositorio.save(producto);

        return "redirect:/tienda";
    }

    @GetMapping("/tienda/borrar/{id}")
    public String borrarProducto(@PathVariable Long id) {

        productoRepositorio.deleteById(id);

        return "redirect:/tienda";
    }

    @ModelAttribute("carrito")
    public Carrito carrito() {
        return new Carrito();
    }

    @GetMapping("/carrito")
    public String verCarrito() {
        return "carrito";
    }

    @PostMapping("/carrito/agregar/{id}")
    public String agregarAlCarrito(
            @PathVariable Long id,
            @ModelAttribute("carrito") Carrito carrito) {

        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        carrito.agregarProducto(producto);

        return "redirect:/tienda";
    }

    @PostMapping("/carrito/eliminar/{id}")
    public String eliminarDelCarrito(
            @PathVariable Long id,
            @ModelAttribute("carrito") Carrito carrito) {

        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        carrito.eliminarProducto(producto);

        return "redirect:/carrito";
    }

    @PostMapping("/carrito/vaciar")
    public String vaciarCarrito(@ModelAttribute("carrito") Carrito carrito) {
        carrito.vaciar();
        return "redirect:/carrito";
    }

}
