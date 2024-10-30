package com.example.servlet;


import com.example.dao.AdministratorDAO;
import com.example.dao.AlumnoDAO;
import com.example.dao.TutorDAO;
import com.example.model.Administrador;
import com.example.model.Alumno;
import com.example.model.Tutor;
import com.example.service.CambiarEstadoCuentaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/CambiarEstadoCuentaServlet")

public class CambiarEstadoCuentaServlet extends HttpServlet {

    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private TutorDAO tutorDAO = new TutorDAO();
    private AdministratorDAO administradorDAO = new AdministratorDAO();
    // Crear instancia del servicio
    private CambiarEstadoCuentaService cambiarEstadoCuentaService = new CambiarEstadoCuentaService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Alumno> alumnos = alumnoDAO.getAllAlumnos();
        List<Tutor> tutores = tutorDAO.getAllTutoresWithDetails();
        List<Administrador> administradores = administradorDAO.getAllAdministradores();


        request.setAttribute("alumnos", alumnos);
        request.setAttribute("tutores", tutores);
        request.setAttribute("administradores", administradores);

        request.getRequestDispatcher("/Administrator/Listado.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userId = request.getParameter("userId");
        String accion = request.getParameter("accion");
        String typeUser = request.getParameter("typeUser");
        // Delegar la lógica de negocio al servicio
        cambiarEstadoCuentaService.cambiarEstadoCuenta(userId, accion, typeUser);




        doGet(request, response);

    }


}
