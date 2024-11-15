package com.example.service;

import com.example.dao.AlumnoDAO;
import com.example.dao.TutorDAO;
import com.example.dao.AdministratorDAO;
import com.example.dao.UsuarioDAO;
import com.example.model.Alumno;
import com.example.model.Tutor;
import com.example.model.Administrador;
import com.example.model.Usuario;

public class SeleccionRolService {

    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private TutorDAO tutorDAO = new TutorDAO();
    private AdministratorDAO administradorDAO = new AdministratorDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public String obtenerRedireccionPorRol(int rolId) {
        if (rolId == 3) { // Por ejemplo, 1 es Admin
            return "Administrator/Administrador.jsp";
        } else if (rolId == 1) { // 2 es Usuario
            return "User/usuario.jsp";
        } else if (rolId == 2) { // 3 es Tutor
            return "Tutor/tutor.jsp";
        } else {
            return null;
        }
    }
    public String obtenerEstadoCuenta(int rolId, int referenciaId) {
        switch (rolId) {
            case 1: // Estudiante
                return alumnoDAO.obtenerEstadoCuenta(referenciaId);
            case 2: // Tutor
                return tutorDAO.obtenerEstadoCuenta(referenciaId);
            case 3: // Administrador
                return administradorDAO.obtenerEstadoCuenta(referenciaId);
            default:
                return null;
        }
    }

    public Usuario validarCredenciales(String nombreUsuario, String contrasena) {
        Usuario usuario = usuarioDAO.findByNombreUsuario(nombreUsuario);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            return usuario;
        }
        return null;
    }

    // Nuevo método para obtener nombre y apellido en base al rol y referencia ID
    public String[] obtenerNombreYApellido(int rolId, int referenciaId) {
        switch (rolId) {
            case 3: // Administrador
                Administrador admin = administradorDAO.findById(referenciaId);
                return new String[]{admin.getNombre(), admin.getApellido()};
            case 1: // Alumno
                Alumno alumno = alumnoDAO.findById(referenciaId);
                return new String[]{alumno.getNombre(), alumno.getApellido()};
            case 2: // Tutor
                Tutor tutor = tutorDAO.findById(referenciaId);
                return new String[]{tutor.getNombre(), tutor.getApellido()};
            default:
                return new String[]{"Desconocido", "Desconocido"};
        }
    }
}
