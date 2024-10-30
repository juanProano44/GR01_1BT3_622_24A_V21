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
        registrarTutoriaService = new RegistrarTutoriaService();// Inicializar materiaDAO
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int codigoMateria = Integer.parseInt(request.getParameter("materia"));
        String fecha = request.getParameter("fecha");
        String horaInicio = request.getParameter("horaInicioSelect");
        String horaFin = request.getParameter("horaFinSelect");

        int tutorId = 1;  // ID del tutor

        registrarTutoriaService.registrarTutoria(codigoMateria, fecha, horaInicio, horaFin, tutorId);

        // Redirigir
        response.sendRedirect(request.getContextPath() + "/Tutor/tutor.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Materia> materias = materiaDAO.getMateriasByTutorId(1);

        // Pasar la lista de materias al JSP
        request.setAttribute("materias", materias);
        request.getRequestDispatcher("/Tutor/registrarTutoria.jsp").forward(request, response);

    }
}
