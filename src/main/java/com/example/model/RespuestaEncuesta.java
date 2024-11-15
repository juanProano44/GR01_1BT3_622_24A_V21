package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "respuesta_encuesta")
public class RespuestaEncuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private Solicitud solicitud;

    @ManyToOne
    @JoinColumn(name = "codigomateria", nullable = false)
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "pregunta_id", nullable = false) // Agrega la columna para la relación con PreguntasEncuesta
    private PreguntasEncuesta pregunta;

    @Column(name = "calificacion", nullable = false)
    private int calificacion; // Valor de 1 a 5 en escala de Likert

    public PreguntasEncuesta getPregunta() {
        return pregunta;
    }

    public void setPregunta(PreguntasEncuesta pregunta) {
        this.pregunta = pregunta;
    }
// Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
}
