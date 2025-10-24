package com.devsenior.luistriana.leer_mas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsenior.luistriana.leer_mas.model.entity.Libro;
import com.devsenior.luistriana.leer_mas.service.Libreria;
import com.devsenior.luistriana.leer_mas.service.LibreriaImpl;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/libreria")
public class LibreriaController {
    private final Libreria libreria;

    public LibreriaController(LibreriaImpl libreria) {
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

    @PostMapping("/nuevolibro")
    public Libro agregarUnNuevoLibro(@Valid @RequestBody Libro nuevoLibro) {
         libreria.agregarLibro(nuevoLibro);
        return nuevoLibro;
    }

}
