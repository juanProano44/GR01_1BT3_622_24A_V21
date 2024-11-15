package com.example.servlet;

import com.example.service.RegistroUsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/RegistroUsuarioServlet")
@MultipartConfig // Permite la carga de archivos en el formulario
public class RegistroUsuarioServlet extends HttpServlet {

    private final RegistroUsuarioService registroUsuarioService = new RegistroUsuarioService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recoger los parámetros del formulario
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String rolSeleccionado = request.getParameter("rol");
        String rolId = "1"; // Valor por defecto para "Estudiante"
        String nombreUsuario = request.getParameter("nombreUsuario");
        String contrasena = request.getParameter("contrasena");

        // Validar si el usuario seleccionó "Tutor" como rol (rolId = 2)
        Part archivoPDFPart = null;
        InputStream archivoPDFInputStream = null;

        if ("2".equals(rolSeleccionado)) { // 2 representa el rol de "Tutor"
            rolId = "2";
            archivoPDFPart = request.getPart("archivoCertificacion");

            // Verificar si el archivo fue enviado
            if (archivoPDFPart == null || archivoPDFPart.getSize() == 0) {
                request.setAttribute("errorMessage", "Debe cargar un archivo PDF para registrarse como tutor.");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }

            // Obtener el InputStream del archivo PDF
            archivoPDFInputStream = archivoPDFPart.getInputStream();
        }

        try {
            // Pasar el InputStream del archivo PDF solo si el rol es Tutor
            registroUsuarioService.registrarUsuario(nombre, apellido, correo, rolId, nombreUsuario, contrasena, archivoPDFInputStream);

            // Redirigir al usuario después de un registro exitoso
            response.sendRedirect(request.getContextPath() + "/index.jsp?registroExitoso=true");

        } catch (Exception e) {
            // Manejo de errores en el registro
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al registrar el usuario: " + e.getMessage());
            request.getRequestDispatcher("/registro.jsp").forward(request, response);
        }
    }
}
