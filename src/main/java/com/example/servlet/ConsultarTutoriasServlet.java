package com.example.servlet;

import com.example.dao.TutoriaDAO;
import com.example.model.Tutoria;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

//@WebServlet("/User/consultarTutorias")
public class ConsultarTutoriasServlet extends HttpServlet {

    private TutoriaDAO tutoriaDAO;

    @Override
    public void init() {
        tutoriaDAO = new TutoriaDAO(); // Inicializar el DAO que maneja las tutorías
    }

    @Override
    // Lógica en el Servlet que muestra las tutorías disponibles (ConsultarTutoriasServlet)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int alumnoId = 1; // Asumimos que el alumno tiene el ID 1

        // Obtener las tutorías disponibles que el alumno no ha aceptado todavía
        List<Tutoria> tutoriasDisponibles = tutoriaDAO.buscarTutoriasDisponibles(alumnoId);

        request.setAttribute("tutorias", tutoriasDisponibles);
        request.getRequestDispatcher("/User/tutoriasDisponibles.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirigir la solicitud POST a doGet para simplificar el manejo
        doGet(request, response);
    }
}
