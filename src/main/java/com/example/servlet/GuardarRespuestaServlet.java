package com.example.servlet;

import com.example.dao.RespuestaEncuestaDAO;
import com.example.model.RespuestaEncuesta;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/guardarRespuesta")
public class GuardarRespuestaServlet extends HttpServlet {

    private final RespuestaEncuestaDAO respuestaEncuestaDAO = new RespuestaEncuestaDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los parámetros como cadenas
        String idSolicitudParam = request.getParameter("idSolicitud");
        String idAlumnoParam = request.getParameter("idAlumno");
        String idTutorParam = request.getParameter("idTutor");
        String codigomateriaParam = request.getParameter("codigomateria");

        // Validar que los parámetros no estén vacíos
        if (idSolicitudParam == null || idSolicitudParam.isEmpty() ||
                idAlumnoParam == null || idAlumnoParam.isEmpty() ||
                idTutorParam == null || idTutorParam.isEmpty() ||
                codigomateriaParam == null || codigomateriaParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros faltantes o vacíos.");
            return;
        }

        // Convertir los parámetros a enteros
        int idSolicitud = Integer.parseInt(idSolicitudParam);
        int idAlumno = Integer.parseInt(idAlumnoParam);
        int idTutor = Integer.parseInt(idTutorParam);
        int codigomateria = Integer.parseInt(codigomateriaParam);

        // Obtener todos los nombres de parámetros enviados (para calificaciones)
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (paramName.startsWith("calificacion_")) {
                int idPregunta = Integer.parseInt(paramName.split("_")[1]);
                String calificacionParam = request.getParameter(paramName);

                // Validar que la calificación no esté vacía antes de convertirla
                if (calificacionParam == null || calificacionParam.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Calificación vacía para la pregunta con ID: " + idPregunta);
                    return;
                }

                int calificacion = Integer.parseInt(calificacionParam);

                // Crear y guardar la respuesta en la base de datos
                RespuestaEncuesta respuesta = new RespuestaEncuesta();
                respuesta.setCalificacion(calificacion);

                // Establecer los valores de los IDs directamente
                respuesta.setAlumnoId(idAlumno);
                respuesta.setTutorId(idTutor);
                respuesta.setSolicitudId(idSolicitud);
                respuesta.setCodigomateria(codigomateria);

                // Guardar la respuesta en la base de datos
                respuestaEncuestaDAO.saveRespuesta(respuesta);
            }
        }

        // Redirigir después de guardar las respuestas
        response.sendRedirect(request.getContextPath() + "/VerSolicitudesServlet");
    }

}
