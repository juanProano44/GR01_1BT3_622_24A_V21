package com.example.servlet;

import com.example.dao.MateriaDAO;
import com.example.dao.TutoriaDAO;
import com.example.model.Materia;
import com.example.model.Tutoria;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class ConsultarTutoriasServlet extends HttpServlet {

    private TutoriaDAO tutoriaDAO;
    private MateriaDAO materiaDAO;

    @Override
    public void init() {
        tutoriaDAO = new TutoriaDAO();
        materiaDAO = new MateriaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer alumnoId = (Integer) session.getAttribute("userReferenceId");

        if (alumnoId == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // Obtener los filtros desde los parámetros de la solicitud
        String materiaIdParam = request.getParameter("materiaId");
        String ordenFecha = request.getParameter("ordenFecha");

        Integer materiaId = (materiaIdParam != null && !materiaIdParam.isEmpty()) ? Integer.parseInt(materiaIdParam) : null;

        // Obtener las materias para el filtro
        List<Materia> materias = materiaDAO.getAllMaterias();
        request.setAttribute("materias", materias);

        // Obtener las tutorías disponibles aplicando los filtros
        List<Tutoria> tutoriasDisponibles = tutoriaDAO.buscarTutoriasDisponiblesConFiltros(alumnoId, materiaId, ordenFecha);
        request.setAttribute("tutorias", tutoriasDisponibles);

        request.getRequestDispatcher("/User/tutoriasDisponibles.jsp").forward(request, response);
    }
}
