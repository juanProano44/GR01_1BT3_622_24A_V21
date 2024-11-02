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
        //Verificacion de datos de prueba
        Alumno alumno = alumnoDAO.findByEmail(correo);
        if (alumno == null) {
            registroSistemaService.registrarUsuario(nombre, apellido, correo, rolId, materiasSeleccionadas);
        } else {
            alumnoDAO.eliminarAlumno(String.valueOf(alumno.getId()));
            registroSistemaService.registrarUsuario(nombre, apellido, correo, rolId, materiasSeleccionadas);

        }
        // Validar que el alumno fue registrado correctamente
        Alumno alumnoTest = alumnoDAO.findByEmail(correo);
        assertNotNull(alumnoTest, "El alumno debería estar registrado.");
        assertEquals(correo, alumnoTest.getEmail(), "El correo del alumno debería coincidir.");

        //Validar que no se puede crear un alumno con el mismo correo
        Exception exception = assertThrows(RuntimeException.class, () -> {
            registroSistemaService.registrarUsuario(nombre, apellido, correo, rolId, materiasSeleccionadas);
        });
        assertEquals("El correo ya está registrado: " + correo, exception.getMessage());

        // Limpiar: eliminar el registro del alumno después de la prueba
        alumnoDAO.eliminarAlumno(String.valueOf(alumnoTest.getId())); // Asegúrate de que el ID sea un String
        assertNull(alumnoDAO.findByEmail(correo), "El alumno debería haber sido eliminado.");
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

    @Test
    void testRegistrarAdministrador() {
        String nombre = "Kerlly";
        String apellido = "Vizuete";
        String correo = "ale.vizuete@epn.edu.ec";
        String rolId = "3";
        String[] materiasSeleccionadas = new String[0];

        //Verificacion de datos de prueba
        Administrador administrador = administradorDAO.findByEmail(correo);
        if (administrador == null) {
            registroSistemaService.registrarUsuario(nombre, apellido, correo, rolId, materiasSeleccionadas);
        }
        else {
            administradorDAO.eliminarAdmin(String.valueOf(administrador.getId()));
            registroSistemaService.registrarUsuario(nombre, apellido, correo, rolId, materiasSeleccionadas);

        }
        // Validar que el alumno fue registrado correctamente
        Administrador administradorTest = administradorDAO.findByEmail(correo);
        assertNotNull(administradorTest, "El administrador debería estar registrado.");
        assertEquals(correo, administradorTest.getEmail(), "El correo del administrador debería coincidir.");

        //Validar que no se puede crear un alumno con el mismo correo
        Exception exception = assertThrows(RuntimeException.class, () -> {
            registroSistemaService.registrarUsuario(nombre, apellido, correo, rolId, materiasSeleccionadas);
        });
        assertEquals("El correo ya está registrado: " + correo, exception.getMessage());

        // Limpiar: eliminar el registro del alumno después de la prueba
        alumnoDAO.eliminarAlumno(String.valueOf(administradorTest.getId())); // Asegúrate de que el ID sea un String
        assertNull(alumnoDAO.findByEmail(correo), "El administrador debería haber sido eliminado.");
    }
}