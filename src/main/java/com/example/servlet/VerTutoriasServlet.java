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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/VerTutoriasServlet")
public class VerTutoriasServlet extends HttpServlet {

    private TutoriaDAO tutoriaDAO;
    private SolicitudDAO solicitudDAO;

    @Override
    public void init() {
        tutoriaDAO = new TutoriaDAO();
        solicitudDAO = new SolicitudDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer tutorId = (Integer) session.getAttribute("userReferenceId");

        if (tutorId == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String estado = request.getParameter("estado");
        List<Tutoria> tutorias = tutoriaDAO.listarSolicitudesPendientes(tutorId);
        List<Solicitud> solicitudes;

        if (estado != null && !estado.isEmpty()) {
            solicitudes = solicitudDAO.getSolicitudesPorEstadoYtutor(tutorId, estado);
        } else {
            solicitudes = solicitudDAO.getSolicitudesPorTutor(tutorId);
        }

        // Crear un mapa para almacenar el estado de los cupos completos para cada tutoría
        Map<Integer, Boolean> cuposCompletosMap = new HashMap<>();
        Map<Integer, Integer> solicitudesAceptadasMap = new HashMap<>();

        // Validar cupos para cada tutoría
        for (Tutoria tutoria : tutorias) {
            int solicitudesAceptadas = solicitudDAO.contarSolicitudesAceptadasPorTutoria(tutoria.getId());
            boolean cuposCompletos = solicitudesAceptadas >= tutoria.getCupos();
            cuposCompletosMap.put(tutoria.getId(), cuposCompletos);
            solicitudesAceptadasMap.put(tutoria.getId(), solicitudesAceptadas); // Guardar el número de solicitudes aceptadas

        }

        request.setAttribute("tutorias", tutorias);
        request.setAttribute("solicitudes", solicitudes);
        request.setAttribute("estadoSeleccionado", estado);
        request.setAttribute("cuposCompletosMap", cuposCompletosMap); // Enviar el mapa al JSP
        request.setAttribute("solicitudesAceptadasMap", solicitudesAceptadasMap);

        request.getRequestDispatcher("/Tutor/verTutorias.jsp").forward(request, response);
    }
}
