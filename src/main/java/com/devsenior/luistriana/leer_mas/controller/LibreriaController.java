package com.devsenior.luistriana.leer_mas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsenior.luistriana.leer_mas.model.entity.Libro;
import com.devsenior.luistriana.leer_mas.service.Libreria;


import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/libreria")
public class LibreriaController {
    private final Libreria libreria;

    public LibreriaController(Libreria libreria) {
        this.libreria = libreria;
    }

    // trae todos los libros
    @GetMapping("/libros")
    public List<Libro> verTodosLosLibros() {
        return libreria.todosLosLibros();
    }

    // buscar libro por isbn
    @GetMapping("/libro/{isbn}")
    public Libro buscarLibroPorIsbn(@PathVariable String isbn) {
        return libreria.buscarLibroPorIsbn(isbn);
    }

    // añadir nuevo libro
    @PostMapping("/nuevolibro")
    public Libro agregarUnNuevoLibro(@Valid @RequestBody Libro nuevoLibro) {
        libreria.agregarLibro(nuevoLibro);
        return nuevoLibro;
    }

    // actualizar todo el libro
    @PutMapping("actualizar/libro/{isbn}")
    public void actualizarLibro(@PathVariable String isbn, @RequestBody Libro libro) {
        libreria.actualizarLibro(isbn, libro);
    }

    // eliminar un libro
    @DeleteMapping("/libro/{isbn}")
    public void eliminarLibro(@PathVariable String isbn) {
        libreria.eliminarLibro(isbn);

    }

    // actualizar parcialmente un libro.
    @PatchMapping("/libro/{isbn}")
    public void actualizarLibroParcialmente(@PathVariable String isbn, @RequestBody Libro libro) {
        libreria.actualizarLibroParcialmente(isbn, libro);
    }

}
