package com.example.servlet;

import com.example.service.SeleccionRolService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SeleccionRolServlet")
public class SeleccionRolServlet extends HttpServlet {

    private SeleccionRolService seleccionRolService;

    public void init() {
        seleccionRolService = new SeleccionRolService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No se necesita implementación aquí en este caso
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rol = request.getParameter("rol");

        // Obtener la página de redirección según el rol usando el servicio
        String redireccion = seleccionRolService.obtenerRedireccionPorRol(rol);

        if (redireccion != null) {
            response.sendRedirect(redireccion);
        } else {
            // Manejar el caso donde el rol no es válido (opcional)
            response.sendRedirect("error.jsp"); // Por ejemplo, una página de error genérica
        }
    }
}
