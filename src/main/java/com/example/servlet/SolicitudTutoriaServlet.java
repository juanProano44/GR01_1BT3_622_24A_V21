package com.example.servlet;

import com.example.dao.SolicitudTutoriaDAO;
import com.example.dao.TutoriaDAO;
import com.example.model.Alumno;
import com.example.model.SolicitudTutoria;
import com.example.model.Tutoria;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/SolicitudTutoriaServlet")
public class SolicitudTutoriaServlet extends HttpServlet {

    private TutoriaDAO tutoriaDAO;
    private SolicitudTutoriaDAO solicitudTutoriaDAO;

    public void init() {
        tutoriaDAO = new TutoriaDAO();
        solicitudTutoriaDAO = new SolicitudTutoriaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = "list";
        }

        switch (action) {
            case "list":
                listTutoriasDisponibles(request, response);
                break;
            case "aceptar":
                aceptarTutoria(request, response);
                break;
            default:
                listTutoriasDisponibles(request, response);
                break;
        }
    }

    private void listTutoriasDisponibles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tutoria> tutorias = tutoriaDAO.getAllTutorias();
        request.setAttribute("tutorias", tutorias);
        request.getRequestDispatcher("User/tutoriasDisponibles.jsp").forward(request, response);
    }

    private void aceptarTutoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int alumnoId = Integer.parseInt(request.getParameter("alumnoId")); // El ID del alumno
        int tutoriaId = Integer.parseInt(request.getParameter("tutoriaId")); // La tutoría a aceptar

        Alumno alumno = new Alumno(); // Este alumno debería estar registrado o obtenerse de la sesión
        alumno.setId(alumnoId);

        Tutoria tutoria = tutoriaDAO.getTutoriaById(tutoriaId);

        SolicitudTutoria solicitud = solicitudTutoriaDAO.getSolicitudByAlumnoAndTutoria(alumnoId, tutoriaId);
        if (solicitud == null) {
            solicitud = new SolicitudTutoria();
            solicitud.setAlumno(alumno);
            solicitud.setTutoria(tutoria);
        }

        solicitudTutoriaDAO.aceptarTutoria(solicitud);
        response.sendRedirect("SolicitudTutoriaServlet?action=list");
    }
}
