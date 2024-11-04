package com.example.servlet;

import com.example.dao.SolicitudTutoriaDAO;
import com.example.dao.TutoriaDAO;
import com.example.model.Tutoria;
import com.example.service.SolicitudTutoriaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/SolicitudTutoriaServlet")
public class SolicitudTutoriaServlet extends HttpServlet {

    private SolicitudTutoriaService solicitudTutoriaService;

    public void init() {
        solicitudTutoriaService = new SolicitudTutoriaService(new TutoriaDAO(), new SolicitudTutoriaDAO());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        List<Tutoria> tutorias = solicitudTutoriaService.listarTutoriasDisponibles();
        if (action == null || action.isEmpty()) {
            action = "list";
        }

        switch (action) {
            case "list":
                request.setAttribute("tutorias", tutorias);
                request.getRequestDispatcher("User/tutoriasDisponibles.jsp").forward(request, response);
                break;
            case "aceptar":
                int alumnoId = Integer.parseInt(request.getParameter("alumnoId"));
                int tutoriaId = Integer.parseInt(request.getParameter("tutoriaId"));
                solicitudTutoriaService.aceptarTutoria(alumnoId, tutoriaId);
                response.sendRedirect("SolicitudTutoriaServlet?action=list");
                break;
            default:

                request.setAttribute("tutorias", tutorias);
                request.getRequestDispatcher("User/tutoriasDisponibles.jsp").forward(request, response);
                break;
        }
    }

}
