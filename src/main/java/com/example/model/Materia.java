package com.example.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "materia")
public class Materia {

    @Id
    private int codigomateria;
    private String nombre;
    private String descripcion;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "materias")
    private List<Tutor> tutores;

    public List<Tutor> getTutores() {
        return tutores;
    }

    public void setTutores(List<Tutor> tutores) {
        this.tutores = tutores;
    }

    // Getters y Setters
    public int getCodigomateria() {
        return codigomateria;
    }

    public void setCodigomateria(int codigomateria) {
        this.codigomateria = codigomateria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
