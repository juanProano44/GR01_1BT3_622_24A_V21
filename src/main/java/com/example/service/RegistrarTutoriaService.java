package com.example.service;

import com.example.dao.TutoriaDAO;
import com.example.dao.MateriaDAO;
import com.example.model.Materia;
import com.example.model.Tutor;
import com.example.model.Tutoria;

import java.sql.Date;
import java.util.List;

public class RegistrarTutoriaService {

    private TutoriaDAO tutoriaDAO;
    private MateriaDAO materiaDAO;

    // Constructor vacío por defecto (por si lo necesitas para otras partes de tu aplicación)
    public RegistrarTutoriaService() {
        this.tutoriaDAO = new TutoriaDAO();  // Inicializa con implementaciones reales
        this.materiaDAO = new MateriaDAO();  // Inicializa con implementaciones reales
    }

    // Constructor que acepta los DAOs (para facilitar los tests)
    public RegistrarTutoriaService(TutoriaDAO tutoriaDAO, MateriaDAO materiaDAO) {
        this.tutoriaDAO = tutoriaDAO;
        this.materiaDAO = materiaDAO;
    }

    // Método para registrar la tutoría
    public void registrarTutoria(int codigoMateria, String fecha, String horaInicio, String horaFin, int tutorId) {
        Tutoria tutoria = new Tutoria();
        Materia materia = new Materia();
        materia.setCodigomateria(codigoMateria);

        tutoria.setMateria(materia);
        tutoria.setFecha(Date.valueOf(fecha));
        tutoria.setHoraInicio(horaInicio + ":00");
        tutoria.setHoraFin(horaFin + ":00");

        Tutor tutor = new Tutor();
        tutor.setId(tutorId);
        tutoria.setTutor(tutor);

        tutoriaDAO.reguistarTutoria(tutoria);
    }

    // Método para obtener las materias que puede impartir un tutor
    public List<Materia> obtenerMateriasPorTutor(int tutorId) {
        return materiaDAO.getMateriasByTutorId(tutorId);
    }
}
