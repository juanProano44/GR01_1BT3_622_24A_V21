package com.example.servlet;

import com.example.dao.TutoriaDAO;
import com.example.dao.MateriaDAO;
import com.example.model.Materia;
import com.example.model.Tutor;
import com.example.model.Tutoria;
import com.example.service.RegistrarTutoriaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;


@WebServlet("/RegistrarTutoriaServlet")
public class RegistrarTutoriaServlet extends HttpServlet {

    private TutoriaDAO tutoriaDAO;
    private MateriaDAO materiaDAO; // Definir la variable materiaDAO
    private RegistrarTutoriaService registrarTutoriaService;

    public void init() {
        tutoriaDAO = new TutoriaDAO();
        materiaDAO = new MateriaDAO();
        registrarTutoriaService = new RegistrarTutoriaService(); // Inicializar materiaDAO
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la sesión y el ID del tutor en la sesión
        HttpSession session = request.getSession();
        Integer tutorId = (Integer) session.getAttribute("userReferenceId"); // ID específico del tutor

        if (tutorId == null) {
            // Si no hay tutor en sesión, redirigir al login
            response.sendRedirect("index.jsp");
            return;
        }

        // Obtener los datos del formulario, incluyendo el nuevo campo de cupos
        int codigoMateria = Integer.parseInt(request.getParameter("materia"));
        String fecha = request.getParameter("fecha");
        String horaInicio = request.getParameter("horaInicioSelect");
        String horaFin = request.getParameter("horaFinSelect");
        int cupos = Integer.parseInt(request.getParameter("cupos")); // Obtener el valor de cupos

        // Llamar al servicio para registrar la tutoría con los cupos
        registrarTutoriaService.registrarTutoria(codigoMateria, fecha, horaInicio, horaFin, tutorId, cupos);

        // Redirigir
        response.sendRedirect(request.getContextPath() + "/Tutor/tutor.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el ID del tutor en sesión
        HttpSession session = request.getSession();
        Integer tutorId = (Integer) session.getAttribute("userReferenceId");

        if (tutorId == null) {
            // Si no hay tutor en sesión, redirigir al login
            response.sendRedirect("index.jsp");
            return;
        }
        List<Materia> materias = materiaDAO.getMateriasByTutorId(tutorId);

        // Pasar la lista de materias al JSP
        request.setAttribute("materias", materias);
        request.getRequestDispatcher("/Tutor/registrarTutoria.jsp").forward(request, response);
    }
}

