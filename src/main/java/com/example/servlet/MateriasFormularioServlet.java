package com.example.servlet;

import com.example.dao.MateriaDAO;
import com.example.dao.PreguntasEncuestaDAO;
import com.example.model.Materia;
import com.example.model.PreguntasEncuesta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/materiasFormularioServlet")
public class MateriasFormularioServlet extends HttpServlet {
    public MateriasFormularioServlet() {
    }

    private MateriaDAO materiaDAO;
    private final PreguntasEncuestaDAO preguntasEncuestaDAO = new PreguntasEncuestaDAO();


    @Override
    public void init() {
        materiaDAO = new MateriaDAO();
        System.out.println("MateriasFormularioServlet inicializado correctamente");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Materia> materias = materiaDAO.getAllMaterias();
        request.setAttribute("materias", materias);
        // Obtener el código de materia del parámetro de la solicitud
        String codigomateriaParam = request.getParameter("codigomateria");
        int codigomateria = codigomateriaParam != null ? Integer.parseInt(codigomateriaParam) : materias.get(0).getCodigomateria();
        request.setAttribute("codigomateria", codigomateria);

        // Cargar las preguntas de la materia seleccionada
        List<PreguntasEncuesta> preguntas = preguntasEncuestaDAO.getPreguntasByMateria(codigomateria);
        request.setAttribute("preguntas", preguntas);

        request.getRequestDispatcher("/Administrator/FormularioAdmin.jsp").forward(request, response);
    }

}
