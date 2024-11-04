package com.example.servlet;

import com.example.dao.RespuestaEncuestaDAO;
import com.example.dao.SolicitudDAO;
import com.example.model.RespuestaEncuesta;
import com.example.model.Solicitud;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/VerRespuestasServlet")
public class VerRespuestasServlet extends HttpServlet {

    private final SolicitudDAO solicitudDAO = new SolicitudDAO();
    private final RespuestaEncuestaDAO respuestaEncuestaDAO = new RespuestaEncuestaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idSolicitudParam = request.getParameter("idSolicitud");

        if (idSolicitudParam != null) {
            try {
                int idSolicitud = Integer.parseInt(idSolicitudParam);

                // Obtener la solicitud y las respuestas asociadas
                Solicitud solicitud = solicitudDAO.getSolicitudById(idSolicitud);

                if (solicitud != null) {
                    List<RespuestaEncuesta> respuestas = respuestaEncuestaDAO.getRespuestasPorSolicitud(idSolicitud);

                    // Pasar los datos a la vista
                    request.setAttribute("solicitud", solicitud);
                    request.setAttribute("respuestas", respuestas);

                    // Redirigir a VerRespuestas.jsp
                    request.getRequestDispatcher("/Tutor/VerRespuestas.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/error.jsp?mensaje=Solicitud no encontrada");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/error.jsp?mensaje=ID de solicitud inválido");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp?mensaje=Falta ID de solicitud");
        }
    }
}
