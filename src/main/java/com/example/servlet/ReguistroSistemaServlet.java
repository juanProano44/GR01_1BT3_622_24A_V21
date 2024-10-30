package com.example.servlet;
import com.example.dao.MateriaDAO;
import com.example.model.Materia;
import com.example.dao.RolDAO;
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

    //inializar las variables
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
        String[] materiasSeleccionadas = request.getParameterValues("materiasSeleccionadas");

        try {

            reguistroSistemaService.registrarUsuario(nombre, apellido, correo, rolId, materiasSeleccionadas);
        } catch (Exception e) {

            request.setAttribute("errorMessage", e.getMessage());
            doGet(request, response);
        }
        doGet(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        List<Materia> materias = materiaDAO.getAllMaterias();
        List<Rol> rols = rolDAO.getAllRols();

        //setear al front
        request.setAttribute("rols", rols);
        request.setAttribute("materias", materias);
        request.getRequestDispatcher("/Administrator/ReguistroUsuarios.jsp").forward(request, response);

    }
}
