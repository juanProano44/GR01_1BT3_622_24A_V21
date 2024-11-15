package com.example.service;

import com.example.dao.SolicitudDAO;
import com.example.dao.TutoriaDAO;
import com.example.model.Alumno;
import com.example.model.Solicitud;
import com.example.model.Tutor;
import com.example.model.Tutoria;

public class AceptarTutoriaService {

    private SolicitudDAO solicitudDAO;
    private TutoriaDAO tutoriaDAO;

    public AceptarTutoriaService(SolicitudDAO solicitudDAO, TutoriaDAO tutoriaDAO) {
        this.solicitudDAO = solicitudDAO;
        this.tutoriaDAO = tutoriaDAO;
    }

    public void aceptarTutoria(int tutoriaId, int alumnoId) throws Exception {
        // Obtener la tutoria desde la base de datos para recuperar el tutor asociado
        Tutoria tutoria = tutoriaDAO.findById(tutoriaId);
        if (tutoria == null) {
            throw new Exception("La tutoría no existe");
        }

        int tutorId = tutoria.getTutor().getId(); // Obtener el tutor que creó la tutoría

        // Crear los objetos de entidad correspondientes
        Alumno alumno = new Alumno();
        alumno.setId(alumnoId);

        Tutor tutor = new Tutor();
        tutor.setId(tutorId);

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
