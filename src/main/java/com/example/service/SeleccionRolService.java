package com.example.service;

public class SeleccionRolService {

    public String obtenerRedireccionPorRol(String rol) {
        if ("usuario".equals(rol)) {
            return "User/usuario.jsp";
        } else if ("tutor".equals(rol)) {
            return "Tutor/tutor.jsp";
        } else if ("administrator".equals(rol)) {
            return "Administrator/Administrador.jsp";
        } else {
            return null; // O redirigir a una página de error si el rol no es válido
        }
    }
}
