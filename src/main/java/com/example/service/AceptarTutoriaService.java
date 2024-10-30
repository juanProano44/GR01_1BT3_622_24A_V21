package com.example.service;

import com.example.dao.SolicitudDAO;
import com.example.model.Alumno;
import com.example.model.Solicitud;
import com.example.model.Tutor;
import com.example.model.Tutoria;

public class AceptarTutoriaService {

    private SolicitudDAO solicitudDAO; // Inyectar o inicializar el DAO

    public AceptarTutoriaService(SolicitudDAO solicitudDAO) {
        this.solicitudDAO = new SolicitudDAO(); // Inicializar el DAO
    }

    public void aceptarTutoria(int tutoriaId) throws Exception {
        // Valores quemados para el alumnoId y tutorId
        int alumnoId = 1;
        int tutorId = 1;

        // Crear los objetos de entidad correspondientes
        Alumno alumno = new Alumno();
        alumno.setId(alumnoId);

        Tutor tutor = new Tutor();
        tutor.setId(tutorId);

        Tutoria tutoria = new Tutoria();
        tutoria.setId(tutoriaId);

        // Crear la solicitud de tutoría
        Solicitud solicitud = new Solicitud();
        solicitud.setTutoria(tutoria);
        solicitud.setAlumno(alumno);
        solicitud.setTutor(tutor);
        solicitud.setEstado("Pendiente");

        // Guardar la solicitud en la base de datos
        solicitudDAO.solicitarTutoria(solicitud);
    }
}
