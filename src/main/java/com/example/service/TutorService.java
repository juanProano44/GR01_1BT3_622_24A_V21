package com.example.service;

import com.example.dao.MateriaDAO;
import com.example.dao.TutorDAO;
import com.example.model.Materia;
import com.example.model.Tutor;

import java.util.ArrayList;
import java.util.List;

public class TutorService {

    private final TutorDAO tutorDAO;
    private final MateriaDAO materiaDAO;

    public TutorService() {
        this.tutorDAO = new TutorDAO();
        this.materiaDAO = new MateriaDAO();
    }

    // Método para aprobar el tutor y cambiar su estado a "Activo"
    public void aprobarTutor(int tutorId) {
        tutorDAO.cambiarEstado(String.valueOf(tutorId), "Activo");
    }

    // Método para asignar materias a un tutor
    public void asignarMateriasATutor(int tutorId, String[] materiasSeleccionadas) throws Exception {
        Tutor tutor = tutorDAO.obtenerTutorPorId(String.valueOf(tutorId));
        if (tutor == null) {
            throw new Exception("El tutor con ID " + tutorId + " no existe.");
        }

        List<Materia> materias = new ArrayList<>();
        if (materiasSeleccionadas != null) {
            for (String materiaId : materiasSeleccionadas) {
                Materia materia = materiaDAO.findById(Integer.parseInt(materiaId));
                if (materia != null) {
                    materia.getTutores().add(tutor); // Añadir el tutor a la materia
                    materias.add(materia);
                } else {
                    throw new RuntimeException("Materia no encontrada con ID: " + materiaId);
                }
            }
        }
        tutor.setMaterias(materias);
        tutorDAO.actualizarTutor(tutor); // Guarda el tutor actualizado con las materias asignadas
    }

    public void aprobarTutorYAsignarMaterias(int tutorId, String[] materiasSeleccionadas) throws Exception {
        // Obtener el tutor existente por su ID
        Tutor tutor = tutorDAO.obtenerTutorPorId(String.valueOf(tutorId));
        if (tutor == null) {
            throw new Exception("Tutor no encontrado con ID: " + tutorId);
        }

        // Cambiar el estado del tutor a "Activo"
        tutor.setEstadoCuenta("Activo");

        // Asignar materias al tutor
        List<Materia> materiasAsignadas = new ArrayList<>();
        if (materiasSeleccionadas != null) {
            for (String materiaId : materiasSeleccionadas) {
                Materia materia = materiaDAO.findById(Integer.parseInt(materiaId));
                if (materia != null) {
                    materia.getTutores().add(tutor); // Asociar el tutor a la materia
                    materiasAsignadas.add(materia);
                } else {
                    throw new RuntimeException("Materia no encontrada con ID: " + materiaId);
                }
            }
        }
        tutor.setMaterias(materiasAsignadas);

        // Guardar cambios en la base de datos usando el método de actualización
        tutorDAO.actualizarTutor(tutor);
    }

}
