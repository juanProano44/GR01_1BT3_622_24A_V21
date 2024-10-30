package com.example.servlet;

import com.example.service.ResponderSolicitudTutoriaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/responderSolicitud")
public class ResponderSolicitudTutoriaServlet extends HttpServlet {

    private ResponderSolicitudTutoriaService responderSolicitudTutoriaService;

    public void init() {
        responderSolicitudTutoriaService = new ResponderSolicitudTutoriaService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int solicitudId = Integer.parseInt(request.getParameter("solicitudId"));
        String accion = request.getParameter("accion");

        // Llamar al servicio para responder la solicitud
        responderSolicitudTutoriaService.responderSolicitud(solicitudId, accion);

        // Redirigir de vuelta a la lista de solicitudes
        response.sendRedirect("tutor/tutor.jsp");
    }
}
