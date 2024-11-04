package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "preguntas_encuesta")
public class PreguntasEncuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String pregunta;

    @ManyToOne
    @JoinColumn(name = "codigomateria", nullable = false)
    private Materia materia;

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
}
