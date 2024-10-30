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
        // Obtener las listas de alumnos, tutores y administradores
        List<Alumno> alumnos = alumnoDAO.getAllAlumnos();
        List<Tutor> tutores = tutorDAO.getAllTutoresWithDetails();
        List<Administrador> administradores = administradorDAO.getAllAdministradores();


        // Establecer las listas en la request
        request.setAttribute("alumnos", alumnos);
        request.setAttribute("tutores", tutores);
        request.setAttribute("administradores", administradores);

        // Reenviar a la página JSP
        request.getRequestDispatcher("/Administrator/Listado.jsp").forward(request, response);
        //request.getRequestDispatcher("/roles.jsp").forward(request, response);
    }
}
