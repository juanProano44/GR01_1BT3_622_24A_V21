package com.example.servlet;

import com.example.dao.MateriaDAO;
import com.example.dao.TutorDAO;
import com.example.model.Materia;
import com.example.model.Tutor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/SolicitudeTutoreServlet")
public class SolicitudeTutoreServlet extends HttpServlet {

    private final TutorDAO tutorDAO = new TutorDAO();
    private final MateriaDAO materiaDAO = new MateriaDAO(); // Agregar MateriaDAO para obtener materias

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener tutores con estado "Pendiente a revisión"
        List<Tutor> tutoresPendientes = tutorDAO.getTutoresPendientes();

        // Obtener todas las materias
        List<Materia> materias = materiaDAO.getAllMaterias();

        // Enviar la lista de tutores y materias a la página JSP
        request.setAttribute("tutoresPendientes", tutoresPendientes);
        request.setAttribute("materias", materias); // Pasar la lista de materias al JSP

        request.getRequestDispatcher("/Administrator/SolicitudesTutores.jsp").forward(request, response);
    }
}
