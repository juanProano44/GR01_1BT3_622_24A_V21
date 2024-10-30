package com.example.service;
import com.example.model.*;
import com.example.dao.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegistroSistemaServiceTest {
    private RegistroSistemaService registroSistemaService;
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private TutorDAO tutorDAO = new TutorDAO();
    private AdministratorDAO administradorDAO = new AdministratorDAO();
    private MateriaDAO materiaDAO = new MateriaDAO();

    @BeforeEach
    void setUp() {

        registroSistemaService = new RegistroSistemaService();
    }
    @Test
        void testRegistrarAlumno() {
        String nombre = "Kerlly";
        String apellido = "Vizuete";
        String correo = "kerlly.vizuete@epn.edu.ec";
        String rolId = "1";
        String[] materiasSeleccionadas = new String[0];

        registroSistemaService.registrarUsuario(nombre, apellido, correo, rolId, materiasSeleccionadas);

        // Verificar que la solicitud fue aceptada
        Alumno alumno = alumnoDAO.findByEmail(correo);
        assertEquals("kerlly.vizuete@epn.edu.ec", alumno.getEmail());
    }

    @Test
    void testRolInvalido() {
        String nombre = "Kerlly";
        String apellido = "Vizuete";
        String correo = "ale.vizuete@epn.edu.ec";
        String rolId = "4";
        String[] materiasSeleccionadas = new String[0];

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            registroSistemaService.registrarUsuario(nombre, apellido, correo, rolId, materiasSeleccionadas);
        });
        assertEquals("Rol no válido", exception.getMessage());
    }

}








