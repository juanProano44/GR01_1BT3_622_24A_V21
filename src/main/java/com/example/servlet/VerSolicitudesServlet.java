package com.example.servlet;

import com.example.dao.SolicitudDAO;
import com.example.model.Solicitud;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/VerSolicitudesServlet")  // Cambia el nombre del servlet para reflejar mejor su función
public class VerSolicitudesServlet extends HttpServlet {

    private SolicitudDAO solicitudDAO;

    @Override
    public void init() {
        solicitudDAO = new SolicitudDAO(); // Inicializar DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el ID del alumno (valor quemado temporalmente, puedes obtenerlo de la sesión)
        int alumnoId = 1; // Valor quemado para pruebas

        // Consultar todas las solicitudes del alumno
        List<Solicitud> solicitudes = solicitudDAO.getSolicitudesPorAlumno(alumnoId);

        // Pasar la lista de solicitudes al JSP
        request.setAttribute("solicitudes", solicitudes);

        // Redirigir al JSP que muestra las solicitudes
        request.getRequestDispatcher("/User/verTutoriasAceptadas.jsp").forward(request, response);
    }
}
