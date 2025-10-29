package com.devsenior.luistriana.leer_mas.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.devsenior.luistriana.leer_mas.model.entity.Libro;

@Service
public class LibreriaImpl implements Libreria {
    private Set<Libro> listasLibros;
    private final ModelMapper modelMapper;

    public LibreriaImpl(ModelMapper modelMapper) {
        this.listasLibros = new LinkedHashSet<>();
        this.modelMapper = modelMapper;
    }

    @Override
    public void agregarLibro(Libro libro) {
        listasLibros.add(libro);

    }

    @Override
    public List<Libro> todosLosLibros() {
        return new ArrayList<>(listasLibros);

    }

    @Override
    public Libro buscarLibroPorIsbn(String isbn) {
        return listasLibros.stream()
                .filter(libro -> libro.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("libro con isbn" + isbn + "no fue encontrado"));

    }

    @Override
    public void actualizarLibro(String isbn, Libro libroactualizado) {
        Libro libroExistente = buscarLibroPorIsbn(isbn); // buscar el original

        listasLibros.remove(libroExistente); // remover usando el encontrado
        libroactualizado.setIsbn(isbn); // nos aseguramos que el isbn no cambia
        listasLibros.add(libroactualizado); // agregar el reemplazo

    }

    @Override
    public void eliminarLibro(String isbn) {

        Libro libroEliminado = buscarLibroPorIsbn(isbn);

        listasLibros.remove(libroEliminado);

    }

    @Override
    public void actualizarLibroParcialmente(String isbn, Libro libroactualizado) {
        /* estos son todos los atributos que vamos a poder editar del libro */
        /*
         * forma peligrosa de comparar
         * Libro libroExistente = buscarLibroPorIsbn(isbn);
         * Optional.ofNullable(libro.getTitulo()).ifPresent(libroExistente::setTitulo);
         * Optional.ofNullable(libro.getDisponible()).ifPresent(libroExistente::
         * setDisponible);
         * Optional.ofNullable(libro.getGenero()).ifPresent(libroExistente::setGenero);
         * Optional.ofNullable(libro.getPrecio()).ifPresent(libroExistente::setPrecio);
         * Optional.ofNullable(libro.getAñoPublicacion()).ifPresent(libroExistente::
         * setAñoPublicacion);
         */

        /*
         * mejor forma de comparar
         * 
         * modelMapper.map(libro, libroExistente, skipNullProperties = true);
         */
        Libro libroExistente = buscarLibroPorIsbn(isbn);
        modelMapper.map(libroactualizado, libroExistente);
    }

}
