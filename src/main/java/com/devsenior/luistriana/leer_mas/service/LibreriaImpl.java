package com.devsenior.luistriana.leer_mas.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.springframework.stereotype.Service;

import com.devsenior.luistriana.leer_mas.model.entity.Libro;

@Service
public class LibreriaImpl implements Libreria{
 private Set<Libro> listasLibros;

 
 public LibreriaImpl(){
    this.listasLibros = new LinkedHashSet<>(); 
 }
    @Override
    public void agregarLibro(Libro libro) {
        listasLibros.add(libro);

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'agregarLibro'");
    }

    @Override
    public List<Libro> todosLosLibros() {
        return new ArrayList<>(listasLibros);
        // TODO Auto-generated method stub
       
    }

    @Override
    public Libro buscarLibroPorIsbn(String isbn) {
       return  listasLibros.stream()
        .filter(libro -> libro.getIsbn().equals(isbn))
        .findFirst()
        .orElseThrow(()-> new RuntimeException("libro con isbn"+isbn+ "no fue encontrado"));

    }

    @Override
    public void actualizarLibro(Libro libro) {
        /*como reutilizar el metodo buscarLibro en mi actualizar libro ya que me da error  */
       return listasLibros.buscarLibroPorIsbn().stream()
       .
    }

    @Override
    public void elimnarLibro(String isbn) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'elimnarLibro'");
    }

}
