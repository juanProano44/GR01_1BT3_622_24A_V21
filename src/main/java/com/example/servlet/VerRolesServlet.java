package com.example.servlet;

import com.example.dao.AdministratorDAO;
import com.example.dao.AlumnoDAO;
import com.example.dao.TutorDAO;
import com.example.model.Alumno;
import com.example.model.Tutor;
import com.example.model.Administrador;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/VerRolesServlet")
public class VerRolesServlet extends HttpServlet {

    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private TutorDAO tutorDAO = new TutorDAO();
    private AdministratorDAO administradorDAO = new AdministratorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener las listas de alumnos, tutores y administradores con sus detalles de usuario
        List<Alumno> alumnos = alumnoDAO.getAllAlumnosWithUsers();  // Este método debe cargar la información del usuario asociado
        List<Tutor> tutores = tutorDAO.getAllTutoresWithUsers();    // Similar para tutores
        List<Administrador> administradores = administradorDAO.getAllAdministradoresWithUsers(); // Similar para administradores
        // Imprimir el contenido de las listas en consola
        System.out.println("Lista de Alumnos:");
        for (Alumno alumno : alumnos) {
            System.out.println("ID: " + alumno.getId() + ", Nombre: " + alumno.getNombre() + ", Usuario: " + alumno.getUsuario().getNombreUsuario());
        }

        System.out.println("\nLista de Tutores:");
        for (Tutor tutor : tutores) {
            System.out.println("ID: " + tutor.getId() + ", Nombre: " + tutor.getNombre() + ", Usuario: " + tutor.getUsuario().getNombreUsuario());
        }

        System.out.println("\nLista de Administradores:");
        for (Administrador admin : administradores) {
            System.out.println("ID: " + admin.getId() + ", Nombre: " + admin.getNombre() + ", Usuario: " + admin.getUsuario().getNombreUsuario());
        }

        // Establecer las listas en la request
        request.setAttribute("alumnos", alumnos);
        request.setAttribute("tutores", tutores);
        request.setAttribute("administradores", administradores);

        // Reenviar a la página JSP
        request.getRequestDispatcher("/Administrator/Listado.jsp").forward(request, response);
    }
}
