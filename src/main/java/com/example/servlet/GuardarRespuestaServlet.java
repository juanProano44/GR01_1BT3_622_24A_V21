package com.example.servlet;

import com.example.dao.*;
import com.example.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/guardarRespuesta")
public class GuardarRespuestaServlet extends HttpServlet {

    private final RespuestaEncuestaDAO respuestaEncuestaDAO = new RespuestaEncuestaDAO();
    private final AlumnoDAO alumnoDAO = new AlumnoDAO();
    private final TutorDAO tutorDAO = new TutorDAO();
    private final SolicitudDAO solicitudDAO = new SolicitudDAO();
    private final MateriaDAO materiaDAO = new MateriaDAO();
    private final PreguntasEncuestaDAO preguntaEncuestaDAO = new PreguntasEncuestaDAO(); // Agregar DAO para PreguntaEncuesta

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el ID del alumno desde la sesión
        HttpSession session = request.getSession();
        Integer idAlumno = (Integer) session.getAttribute("userReferenceId");
        if (idAlumno == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de alumno no encontrado en la sesión.");
            return;
        }

        // Obtener y validar otros parámetros necesarios
        String idSolicitudParam = request.getParameter("idSolicitud");
        String idTutorParam = request.getParameter("idTutor");
        String codigomateriaParam = request.getParameter("codigomateria");

        if (idSolicitudParam == null || idTutorParam == null || codigomateriaParam == null ||
                idSolicitudParam.isEmpty() || idTutorParam.isEmpty() || codigomateriaParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros faltantes o vacíos.");
            return;
        }

        // Convertir parámetros a enteros
        int idSolicitud = Integer.parseInt(idSolicitudParam);
        int idTutor = Integer.parseInt(idTutorParam);
        int codigomateria = Integer.parseInt(codigomateriaParam);

        // Obtener las entidades de la base de datos
        Alumno alumno = alumnoDAO.findById(idAlumno);
        Tutor tutor = tutorDAO.findById(idTutor);
        Solicitud solicitud = solicitudDAO.findById(idSolicitud);
        Materia materia = materiaDAO.findById(codigomateria);

        if (alumno == null || tutor == null || solicitud == null || materia == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se encontraron algunas entidades requeridas.");
            return;
        }

        // Procesar y guardar cada respuesta de calificación
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (paramName.startsWith("calificacion_")) {
                int idPregunta = Integer.parseInt(paramName.split("_")[1]);
                String calificacionParam = request.getParameter(paramName);

                if (calificacionParam == null || calificacionParam.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Calificación vacía para la pregunta con ID: " + idPregunta);
                    return;
                }

                int calificacion = Integer.parseInt(calificacionParam);

                // Obtener la pregunta correspondiente
                PreguntasEncuesta pregunta = preguntaEncuestaDAO.findById(idPregunta);
                if (pregunta == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Pregunta no encontrada para ID: " + idPregunta);
                    return;
                }

                // Crear y guardar la respuesta en la base de datos
                RespuestaEncuesta respuesta = new RespuestaEncuesta();
                respuesta.setCalificacion(calificacion);
                respuesta.setAlumno(alumno);
                respuesta.setTutor(tutor);
                respuesta.setSolicitud(solicitud);
                respuesta.setMateria(materia);
                respuesta.setPregunta(pregunta); // Asignar la pregunta

                // Guardar la respuesta en la base de datos
                respuestaEncuestaDAO.saveRespuesta(respuesta);
            }
        }

        // Redirigir después de guardar las respuestas
        response.sendRedirect(request.getContextPath() + "/VerSolicitudesServlet");
    }
}
