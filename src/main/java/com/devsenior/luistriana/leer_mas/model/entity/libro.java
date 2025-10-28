package com.devsenior.luistriana.leer_mas.model.entity;

import org.springframework.stereotype.Component;

import com.devsenior.luistriana.leer_mas.model.GeneroLiterario;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Component
public class Libro {
    // atributos de la clase libro
    @NotBlank(message = "El campo ISBN es obligatorio y no puede estar vacío")
    private String isbn;

    @NotBlank(message = "El campo título es obligatorio y no puede estar vacío")
    private String titulo;

    @NotNull(message = "El campo género literario es obligatorio")
    private GeneroLiterario genero;

    @Min(value = 1900, message = "El año de publicación debe ser mayor o igual a 1900")
    private Integer añoPublicacion;

    @Min(value = 1, message = "El libro debe contener al menos 1 página")
    private Integer numeroPaginas;

    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precio;

    private Boolean disponible = true;

    // construtor vacio
    public Libro() {

    }
    // construtor normal 
    
    public Libro(String isbn, String titulo, GeneroLiterario genero, int añoPublicacion, int numeroPaginas,
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

    public GeneroLiterario getGenero() {
        return genero;
    }

    
    public void setGenero(GeneroLiterario genero) {
        this.genero = genero;
    }

    public Integer getAñoPublicacion() {
        return añoPublicacion;
    }

    
    public void setAñoPublicacion(Integer añoPublicacion) {
        this.añoPublicacion = añoPublicacion;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

   
    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
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
