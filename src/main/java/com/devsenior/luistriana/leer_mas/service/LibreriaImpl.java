package com.devsenior.luistriana.leer_mas.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.devsenior.luistriana.leer_mas.model.entity.Libro;

@Service
public class LibreriaImpl implements Libreria{
 private Set<Libro> listasLibros = new LinkedHashSet<>();
    @Override
    public void agregarLibro(Libro libro) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'agregarLibro'");
    }

    @Override
    public List<Libro> todosLosLibros() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'todosLosLibros'");
    }

    @Override
    public Libro buscarLibroPorIsbn(String isbn) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarLibroPorIsbn'");
    }

    @Override
    public void actualizarLibro(Libro libro) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarLibro'");
    }

    @Override
    public void elimnarLibro(String isbn) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'elimnarLibro'");
    }

}
