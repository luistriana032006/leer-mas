package com.devsenior.luistriana.leer_mas.model.entity;

import org.springframework.stereotype.Component;

import com.devsenior.luistriana.leer_mas.model.generoLiterario;

@Component
public class Libro {
    // atributos de la clase libro
    private String isbn;
    private String titulo;
    private generoLiterario genero;

    private int añoPublicacion;

    private int numeroPaginas;
    private double precio;
    private Boolean disponible = true;

    // construtor vacio
    public Libro() {

    }
    // construtor normal 
    
    public Libro(String isbn, String titulo, generoLiterario genero, int añoPublicacion, int numeroPaginas,
            double precio, Boolean disponible) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.genero = genero;
        this.añoPublicacion = añoPublicacion;
        this.numeroPaginas = numeroPaginas;
        this.precio = precio;
        this.disponible = disponible;
    }


    public String getIsbn() {
        return isbn;
    }

     
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getTitulo() {
        return titulo;
    }

    
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public generoLiterario getGenero() {
        return genero;
    }

    
    public void setGenero(generoLiterario genero) {
        this.genero = genero;
    }

    public int getAñoPublicacion() {
        return añoPublicacion;
    }

    
    public void setAñoPublicacion(int añoPublicacion) {
        this.añoPublicacion = añoPublicacion;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

   
    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    // equals y hashcode

    @Override
    public boolean equals (Object o){
  //1. VERIFICAR SI ES EL MISMO OBJETO EN MEMORIA (optimizacion)
    if (this ==o) return true;

    //2. verificar si el objeto es null o de otra clase
     if (o == null || getClass()!=o.getClass()) return false;

     // 3. hace el cast a libr (ya sabemos que es un libro por la linea anterior)
     Libro libro = (Libro) o;

     //4. comparar los ISBN: dos libros son iguales si tienen el mismo isbn 

     return isbn != null && isbn.equals(libro.isbn);
    }

    // hash code 

    @Override 
    public int hashCode(){
        return isbn != null ? isbn.hashCode() : 0;
    }

    
}
