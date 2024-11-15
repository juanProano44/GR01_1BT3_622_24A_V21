package com.example.servlet;

import com.example.dao.MateriaDAO;
import com.example.dao.RolDAO;
import com.example.model.Materia;
import com.example.model.Rol;
import com.example.service.RegistroSistemaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/ReguistroSistemaServlet")
public class ReguistroSistemaServlet extends HttpServlet {

    // Inicializar las variables
    private MateriaDAO materiaDAO = new MateriaDAO();
    private RolDAO rolDAO = new RolDAO();
    private RegistroSistemaService reguistroSistemaService = new RegistroSistemaService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Obtener los datos del formulario
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String rolId = request.getParameter("rol");
        String nombreUsuario = request.getParameter("nombreUsuario");
        String contrasena = request.getParameter("contrasena");
        String[] materiasSeleccionadas = request.getParameterValues("materiasSeleccionadas");

        try {
            // Registrar usuario
            reguistroSistemaService.registrarUsuario(nombre, apellido, correo, rolId, nombreUsuario, contrasena, materiasSeleccionadas);
            doGet(request, response);
        } catch (Exception e) {
            // En caso de error, redirigir de nuevo a la página con un mensaje de error
            request.setAttribute("errorMessage", e.getMessage());
            doGet(request, response);
            return;
        }

        // Redirigir a la página de confirmación o a la misma página después del registro
        response.sendRedirect(request.getContextPath() + "/Administrator/ReguistroUsuarios.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Obtener lista de materias y roles
        List<Materia> materias = materiaDAO.getAllMaterias();
        List<Rol> rols = rolDAO.getAllRols();

        // Setear los datos para el front
        request.setAttribute("rols", rols);
        request.setAttribute("materias", materias);
        request.getRequestDispatcher("/Administrator/ReguistroUsuarios.jsp").forward(request, response);
    }
}
