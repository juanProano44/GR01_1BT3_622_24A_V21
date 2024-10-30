package com.example.service;

import com.example.dao.AdministratorDAO;
import com.example.dao.AlumnoDAO;
import com.example.dao.MateriaDAO;
import com.example.dao.TutorDAO;
import com.example.model.Administrador;
import com.example.model.Alumno;
import com.example.model.Materia;
import com.example.model.Tutor;

import java.util.ArrayList;
import java.util.List;

public class RegistroSistemaService {
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private TutorDAO tutorDAO = new TutorDAO();
    private AdministratorDAO administradorDAO = new AdministratorDAO();
    private MateriaDAO materiaDAO = new MateriaDAO();

    public RegistroSistemaService() {

    }

    public void registrarUsuario(String nombre, String apellido, String correo, String rolId, String[] materiasSeleccionadas){
        // Validar si el correo ya existe
        boolean correoExistente = false;
        switch (rolId){
            case "1": // Alumno
                if (alumnoDAO.findByEmail(correo) != null) {
                    correoExistente = true;                }
                break;
            case "2": // Tutor
                if (tutorDAO.findByEmail(correo) != null) {
                    correoExistente = true;                }
                break;
            case "3": // Administrador
                if (administradorDAO.findByEmail(correo) != null) {
                    correoExistente = true;                }
                break;
            default:
                throw new IllegalArgumentException("Rol no válido");
        }
        // Si el correo ya existe, lanzar una excepción o manejar el error
        if (correoExistente) {
            throw new RuntimeException("El correo ya está registrado: " + correo);        }
        // Continuar con el registro si el correo no existe
        switch (rolId){
            case "1": // Registrar Alumno
                Alumno alumno = new Alumno();
                alumno.setNombre(nombre);
                alumno.setApellido(apellido);
                alumno.setEmail(correo);
                alumno.setRolID(rolId);
                alumnoDAO.registrarAlumno(alumno);
                break;
            case "2": // Registrar Tutor
                Tutor tutor = new Tutor();
                tutor.setNombre(nombre);
                tutor.setApellido(apellido);
                tutor.setEmail(correo);
                tutor.setRolID(rolId);
                List<Materia> materias = new ArrayList<>(); // Lista de materias a las que se va a asociar el tutor
                for (String materiaId : materiasSeleccionadas) {
                    // Recuperar la materia de la base de datos
                    Materia materia = materiaDAO.findById(Integer.parseInt(materiaId));

                    if (materia != null) {
                        // Asignar el tutor a la materia (relación muchos a muchos)
                        materia.getTutores().add(tutor);
                        materias.add(materia); // Añadir materia a la lista de materias del tutor
                    } else {
                        throw new RuntimeException("Materia no encontrada con ID: " + materiaId);
                    }
                }
                // Asignar las materias al tutor
                tutor.setMaterias(materias);
                tutorDAO.registrarTutor(tutor);
                break;
            case "3": // Registrar Administrador
                Administrador admin = new Administrador();
                admin.setNombre(nombre);
                admin.setApellido(apellido);
                admin.setEmail(correo);
                admin.setRolID(rolId);
                administradorDAO.registrarAdmin(admin);
                break;
            default:
                throw new IllegalArgumentException("Rol no válido");
        }
    }
}










