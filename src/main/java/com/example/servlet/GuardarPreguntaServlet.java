package com.example.servlet;

import com.example.dao.PreguntasEncuestaDAO;
import com.example.model.Materia;
import com.example.model.PreguntasEncuesta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/guardarPregunta")
public class GuardarPreguntaServlet extends HttpServlet {

    private final PreguntasEncuestaDAO preguntasEncuestaDAO = new PreguntasEncuestaDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los datos del formulario
        String preguntaTexto = request.getParameter("pregunta");
        int codigomateria = Integer.parseInt(request.getParameter("codigomateria"));

        // Crear una nueva instancia de PreguntasEncuesta
        PreguntasEncuesta pregunta = new PreguntasEncuesta();
        pregunta.setPregunta(preguntaTexto);

        // Asociar la pregunta con la materia (cargar la materia por su ID)
        Materia materia = new Materia();
        materia.setCodigomateria(codigomateria); // Asigna solo el ID, sin cargar todos los datos
        pregunta.setMateria(materia);

        // Guardar la pregunta en la base de datos
        preguntasEncuestaDAO.savePregunta(pregunta);

        // Redirigir al servlet que carga las materias y muestra FormularioAdmin.jsp
        response.sendRedirect(request.getContextPath() + "/materiasFormularioServlet");
    }
}
