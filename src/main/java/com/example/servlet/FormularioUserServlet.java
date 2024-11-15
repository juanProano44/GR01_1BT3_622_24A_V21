package com.example.servlet;

import com.example.dao.PreguntasEncuestaDAO;
import com.example.dao.SolicitudDAO;
import com.example.dao.RespuestaEncuestaDAO;
import com.example.model.PreguntasEncuesta;
import com.example.model.Solicitud;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/formularioUserServlet")
public class FormularioUserServlet extends HttpServlet {

    private final SolicitudDAO solicitudDAO = new SolicitudDAO();
    private final PreguntasEncuestaDAO preguntasEncuestaDAO = new PreguntasEncuestaDAO();
    private final RespuestaEncuestaDAO respuestaEncuestaDAO = new RespuestaEncuestaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idSolicitudParam = request.getParameter("idSolicitud");

        if (idSolicitudParam != null && !idSolicitudParam.isEmpty()) {
            try {
                int idSolicitud = Integer.parseInt(idSolicitudParam);

                // Verificar si ya existen respuestas para esta solicitud
                if (respuestaEncuestaDAO.existeRespuestaParaSolicitud(idSolicitud)) {
                    response.sendRedirect(request.getContextPath() + "/error.jsp?mensaje=Ya has respondido a esta encuesta");
                    return;
                }

                Solicitud solicitud = solicitudDAO.getSolicitudById(idSolicitud);

                if (solicitud != null) {
                    int codigomateria = solicitud.getTutoria().getMateria().getCodigomateria();
                    int idAlumno = solicitud.getAlumno().getId();
                    int idTutor = solicitud.getTutoria().getTutor().getId();

                    List<PreguntasEncuesta> preguntas = preguntasEncuestaDAO.getPreguntasByMateria(codigomateria);

                    request.setAttribute("preguntas", preguntas);
                    request.setAttribute("idSolicitud", idSolicitud);
                    request.setAttribute("idAlumno", idAlumno);
                    request.setAttribute("idTutor", idTutor);
                    request.setAttribute("codigomateria", codigomateria);

                    request.getRequestDispatcher("/User/FormularioUser.jsp").forward(request, response);
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
