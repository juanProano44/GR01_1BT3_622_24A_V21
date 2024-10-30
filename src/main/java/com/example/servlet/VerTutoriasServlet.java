package com.example.servlet;

import com.example.dao.TutoriaDAO;
import com.example.dao.SolicitudDAO;
import com.example.model.Tutoria;
import com.example.model.Solicitud;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/VerTutoriasServlet")
public class VerTutoriasServlet extends HttpServlet {

    private TutoriaDAO tutoriaDAO;
    private SolicitudDAO solicitudDAO;

    @Override
    public void init() {
        tutoriaDAO = new TutoriaDAO(); // Inicializar DAO de tutorías
        solicitudDAO = new SolicitudDAO(); // Inicializar DAO de solicitudes
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el ID del tutor (puedes obtenerlo de la sesión, aquí usamos uno quemado para pruebas)
        int tutorId = 1; // Valor quemado para pruebas

        // Obtener todas las tutorías creadas por el tutor
        List<Tutoria> tutorias = tutoriaDAO.listarSolicitudesPendientes(tutorId);

        // Obtener todas las solicitudes para las tutorías del tutor
        List<Solicitud> solicitudes = solicitudDAO.getSolicitudesPorTutor(tutorId);

        // Pasar las listas a la vista (JSP)
        request.setAttribute("tutorias", tutorias);
        request.setAttribute("solicitudes", solicitudes);

        // Redirigir a la página JSP que muestra las tutorías creadas y las solicitudes
        request.getRequestDispatcher("/Tutor/verTutorias.jsp").forward(request, response);
    }
}
