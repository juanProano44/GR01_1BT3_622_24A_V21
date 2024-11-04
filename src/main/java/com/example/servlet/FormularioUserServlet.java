package com.example.servlet;

import com.example.dao.PreguntasEncuestaDAO;
import com.example.dao.SolicitudDAO;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el ID de la solicitud desde los parámetros de la solicitud
        String idSolicitudParam = request.getParameter("idSolicitud");

        if (idSolicitudParam != null && !idSolicitudParam.isEmpty()) {
            try {
                int idSolicitud = Integer.parseInt(idSolicitudParam);

                // Obtener la solicitud por su ID
                Solicitud solicitud = solicitudDAO.getSolicitudById(idSolicitud);

                if (solicitud != null) {
                    // Obtener el ID de la materia de la tutoría asociada a la solicitud
                    int codigomateria = solicitud.getTutoria().getMateria().getCodigomateria();
                    int idAlumno = solicitud.getAlumno().getId(); // Obtener el ID del alumno asociado
                    int idTutor = solicitud.getTutoria().getTutor().getId(); // Obtener el ID del tutor asociado

                    // Obtener las preguntas asociadas a la materia
                    List<PreguntasEncuesta> preguntas = preguntasEncuestaDAO.getPreguntasByMateria(codigomateria);

                    // Configurar los atributos para la vista
                    request.setAttribute("preguntas", preguntas);
                    request.setAttribute("idSolicitud", idSolicitud);
                    request.setAttribute("idAlumno", idAlumno);
                    request.setAttribute("idTutor", idTutor);
                    request.setAttribute("codigomateria", codigomateria);

                    // Redirigir a FormularioUser.jsp con los datos necesarios
                    request.getRequestDispatcher("/User/FormularioUser.jsp").forward(request, response);
                } else {
                    // Si no se encuentra la solicitud, redirigir con un mensaje de error
                    response.sendRedirect(request.getContextPath() + "/error.jsp?mensaje=Solicitud no encontrada");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/error.jsp?mensaje=ID de solicitud inválido");
            }
        } else {
            // Redirigir con un mensaje de error si falta el parámetro idSolicitud
            response.sendRedirect(request.getContextPath() + "/error.jsp?mensaje=Falta ID de solicitud");
        }
    }
}
