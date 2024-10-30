package com.example.servlet;

import com.example.dao.SolicitudDAO;
import com.example.model.Solicitud;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/CambiarEstadoSolicitudServlet")
public class CambiarEstadoSolicitudServlet extends HttpServlet {

    private SolicitudDAO solicitudDAO;

    @Override
    public void init() {
        solicitudDAO = new SolicitudDAO(); // Inicializar DAO
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int solicitudId = Integer.parseInt(request.getParameter("solicitudId"));
        String nuevoEstado = request.getParameter("nuevoEstado");

        // Obtener la solicitud por ID
        Solicitud solicitud = solicitudDAO.getById(solicitudId);

        if (solicitud != null) {
            // Cambiar el estado de la solicitud
            solicitud.setEstado(nuevoEstado);
            solicitudDAO.responderSolicitud(solicitud);
        }

        // Redirigir de vuelta a la página de tutorías creadas
        response.sendRedirect(request.getContextPath() + "/VerTutoriasServlet");
    }
}
