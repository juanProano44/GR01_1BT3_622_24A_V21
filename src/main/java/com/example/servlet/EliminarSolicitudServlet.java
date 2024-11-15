package com.example.servlet;

import com.example.dao.SolicitudDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/EliminarSolicitudServlet")
public class EliminarSolicitudServlet extends HttpServlet {

    private SolicitudDAO solicitudDAO;

    @Override
    public void init() {
        solicitudDAO = new SolicitudDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idSolicitudParam = request.getParameter("idSolicitud");

        if (idSolicitudParam != null && !idSolicitudParam.isEmpty()) {
            int idSolicitud = Integer.parseInt(idSolicitudParam);

            // Eliminar la solicitud
            solicitudDAO.eliminarSolicitud(idSolicitud);

            // Redirigir a la página de tutorías aceptadas
            response.sendRedirect(request.getContextPath() + "/VerSolicitudesServlet");
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de solicitud no proporcionado.");
        }
    }
}
