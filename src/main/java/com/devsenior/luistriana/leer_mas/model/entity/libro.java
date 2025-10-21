package com.devsenior.luistriana.leer_mas.model.entity;

import org.springframework.stereotype.Component;

import com.devsenior.luistriana.leer_mas.model.generoLiterario;

@Component
public class libro {
    // atributos de la clase libro
    private String isbn;
    private String titulo;
    private generoLiterario genero;

    private int añoPublicacion;

    private int numeroPaginas;
    private double precio;
    private Boolean disponible = true;

    // construtor vacio
    public libro() {

    }
    // construtor normal 
    
    public libro(String isbn, String titulo, generoLiterario genero, int añoPublicacion, int numeroPaginas,
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

}
