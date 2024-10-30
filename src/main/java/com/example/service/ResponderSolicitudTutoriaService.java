package com.example.service;

import com.example.dao.SolicitudTutoriaDAO;
import com.example.model.SolicitudTutoria;

public class ResponderSolicitudTutoriaService {

    private SolicitudTutoriaDAO solicitudTutoriaDAO;

    // Constructor para inyectar el DAO
    public ResponderSolicitudTutoriaService(SolicitudTutoriaDAO solicitudTutoriaDAO) {
        this.solicitudTutoriaDAO = solicitudTutoriaDAO;
    }

    // Constructor por defecto
    public ResponderSolicitudTutoriaService() {
        this.solicitudTutoriaDAO = new SolicitudTutoriaDAO();
    }

    public void responderSolicitud(int solicitudId, String accion) {
        SolicitudTutoria solicitud = solicitudTutoriaDAO.getById(solicitudId);

        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud con el ID " + solicitudId + " no existe.");
        }
        if ("aceptada".equals(solicitud.getEstado()) || "rechazada".equals(solicitud.getEstado())) {
            return;
        }
        if ("aceptar".equals(accion)) {
            solicitud.setEstado("aceptada");
        } else if ("rechazar".equals(accion)) {
            solicitud.setEstado("rechazada");
        }
        solicitudTutoriaDAO.update(solicitud);
    }
}

