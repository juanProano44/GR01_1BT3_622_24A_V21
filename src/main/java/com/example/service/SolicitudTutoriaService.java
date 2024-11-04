package com.example.service;

import com.example.dao.SolicitudTutoriaDAO;
import com.example.dao.TutoriaDAO;
import com.example.model.Alumno;
import com.example.model.SolicitudTutoria;
import com.example.model.Tutoria;

import java.util.List;

public class SolicitudTutoriaService {

    private final TutoriaDAO tutoriaDAO;
    private final SolicitudTutoriaDAO solicitudTutoriaDAO;

    public SolicitudTutoriaService(TutoriaDAO tutoriaDAO, SolicitudTutoriaDAO solicitudTutoriaDAO) {
        this.tutoriaDAO = tutoriaDAO;
        this.solicitudTutoriaDAO = solicitudTutoriaDAO;
    }

    public void aceptarTutoria(int alumnoId, int tutoriaId) {
        Alumno alumno = new Alumno();
        alumno.setId(alumnoId);

        Tutoria tutoria = tutoriaDAO.getTutoriaById(tutoriaId);

        if (tutoria != null) {
            SolicitudTutoria solicitud = solicitudTutoriaDAO.getSolicitudByAlumnoAndTutoria(alumnoId, tutoriaId);
            if (solicitud == null) {
                solicitud = new SolicitudTutoria();
                solicitud.setAlumno(alumno);
                solicitud.setTutoria(tutoria);
            }
            solicitudTutoriaDAO.aceptarTutoria(solicitud);
        }
    }
    public List<Tutoria> listarTutoriasDisponibles() {
        return tutoriaDAO.getAllTutorias();
    }
}
