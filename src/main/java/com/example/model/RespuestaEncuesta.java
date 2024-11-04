package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "respuesta_encuesta")
public class RespuestaEncuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "alumno_id", nullable = false)
    private int alumnoId;

    @Column(name = "tutor_id", nullable = false)
    private int tutorId;

    @Column(name = "solicitud_id", nullable = false)
    private int solicitudId;

    @Column(name = "codigomateria", nullable = false)
    private int codigomateria;

    @Column(name = "calificacion", nullable = false)
    private int calificacion; // Valor de 1 a 5 en escala de Likert

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public int getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(int solicitudId) {
        this.solicitudId = solicitudId;
    }

    public int getCodigomateria() {
        return codigomateria;
    }

    public void setCodigomateria(int codigomateria) {
        this.codigomateria = codigomateria;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
}
