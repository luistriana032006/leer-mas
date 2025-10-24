package com.devsenior.luistriana.leer_mas.service;

import java.util.List;

import com.devsenior.luistriana.leer_mas.model.entity.Libro;

public interface Libreria {
 // AGREGAR UN NUEVO LIBRO 

 void agregarLibro(Libro libro);

 // consultar todos los libros 
  List<Libro> todosLosLibros();
  // buscar libro por isbn

  Libro buscarLibroPorIsbn(String isbn);

  // actualizar informaicion de un libro 

  void actualizarLibro(Libro libro);

  // eliminar un libro
  void eliminarLibro(Libro libro);
}
