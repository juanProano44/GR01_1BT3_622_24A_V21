package com.example.service;

import com.example.dao.AlumnoDAO;
import com.example.dao.TutorDAO;
import com.example.dao.AdministratorDAO;

public class CambiarEstadoCuentaService {

    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private TutorDAO tutorDAO = new TutorDAO();
    private AdministratorDAO administradorDAO = new AdministratorDAO();
    public CambiarEstadoCuentaService() {
        // Constructor predeterminado
    }



    // Método para cambiar el estado de la cuenta basado en el tipo de usuario y la acción
    public void cambiarEstadoCuenta(String userId, String accion, String typeUser) {
        // Verificar la acción seleccionada
        if ("banear".equals(accion)) {
            banearCuenta(userId, typeUser);
        } else if ("eliminar".equals(accion)) {
            eliminarCuenta(userId, typeUser);
        }else {
            // Lanzar excepción si la acción no es válida
            throw new IllegalArgumentException("Acción no válida: " + accion);
        }
    }

    // Método para banear una cuenta (se puede cambiar el estado a "baneado")
    private void banearCuenta(String userId, String typeUser) {
        switch (typeUser) {
            case "alumno":
                alumnoDAO.cambiarEstado(userId, "baneado");
                break;
            case "tutor":
                tutorDAO.cambiarEstado(userId, "baneado");
                break;
            case "admin":
                administradorDAO.cambiarEstado(userId, "baneado");
                break;
        }
    }

    // Método para eliminar una cuenta (se puede eliminar completamente o cambiar el estado)
    private void eliminarCuenta(String userId, String typeUser) {
        switch (typeUser) {
            case "alumno":
                alumnoDAO.eliminarAlumno(userId);
                break;
            case "tutor":
                tutorDAO.eliminarTutor(userId);
                break;
            case "admin":
                administradorDAO.eliminarAdmin(userId);
                break;
        }
    }
}
