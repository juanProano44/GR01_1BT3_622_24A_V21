package com.example.servlet;

import com.example.dao.SolicitudDAO;
import com.example.dao.TutoriaDAO;
import com.example.service.AceptarTutoriaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/AceptarTutoriaServlet")
public class AceptarTutoriaServlet extends HttpServlet {

    private SolicitudDAO solicitudDAO;
    private TutoriaDAO tutoriaDAO;
    private AceptarTutoriaService aceptarTutoriaService;

    @Override
    public void init() {
        solicitudDAO = new SolicitudDAO();
        tutoriaDAO = new TutoriaDAO();
        aceptarTutoriaService = new AceptarTutoriaService(solicitudDAO, tutoriaDAO);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int tutoriaId = Integer.parseInt(request.getParameter("tutoriaId"));
        HttpSession session = request.getSession();
        Integer alumnoId = (Integer) session.getAttribute("userReferenceId");

        if (alumnoId == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        try {
            aceptarTutoriaService.aceptarTutoria(tutoriaId, alumnoId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect(request.getContextPath() + "/User/consultarTutorias");
    }
}

