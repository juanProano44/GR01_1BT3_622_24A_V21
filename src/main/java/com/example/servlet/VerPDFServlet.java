package com.example.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;


@WebServlet("/verPDF")
public class VerPDFServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la ruta del archivo PDF desde el parámetro de la solicitud
        String rutaPdf = request.getParameter("ruta");

        // Validar que la ruta no sea nula ni vacía
        if (rutaPdf == null || rutaPdf.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ruta del archivo PDF no proporcionada.");
            return;
        }

        // Crear el objeto File para el archivo PDF
        File pdfFile = new File(rutaPdf);

        // Verificar que el archivo exista
        if (!pdfFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "El archivo PDF no se encuentra en la ruta especificada.");
            return;
        }

        // Configurar la respuesta para que el contenido sea un PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=" + pdfFile.getName());
        response.setContentLength((int) pdfFile.length());

        // Escribir el contenido del archivo PDF en el output stream de la respuesta
        try (FileInputStream inputStream = new FileInputStream(pdfFile); OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
