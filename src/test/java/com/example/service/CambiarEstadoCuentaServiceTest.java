package com.example.service;

import com.example.dao.AlumnoDAO;
import com.example.dao.TutorDAO;
import com.example.model.Alumno;
import com.example.model.Tutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CambiarEstadoCuentaServiceTest {

    private CambiarEstadoCuentaService cambiarEstadoCuentaService;
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private TutorDAO tutorDAO = new TutorDAO();

    @BeforeEach
    void setUp() {

        cambiarEstadoCuentaService = new CambiarEstadoCuentaService();
    }

    @Test
    void testBanearTutor() {
        String userId = "1";
        String accion = "banear";
        String typeUser = "tutor";

        cambiarEstadoCuentaService.cambiarEstadoCuenta(userId, accion, typeUser);

        // Verificar el estado del alumno
        Tutor tutor = tutorDAO.obtenerTutorPorId(userId);
        assertEquals("baneado", tutor.getEstadoCuenta());
    }

    @Test
    void testEliminarAlumno() {
        String userId = "2";
        String accion = "eliminar";
        String typeUser = "alumno";

        cambiarEstadoCuentaService.cambiarEstadoCuenta(userId, accion, typeUser);

        // Verificar que el tutor fue eliminado
        Alumno alumno = alumnoDAO.obtenerAlumnoPorId(userId);
        assertNull(alumno);  // El tutor debe haber sido eliminado
    }

    @Test
    void testBanearUsuarioInexistente() {
        String userId = "9999"; // Usuario inexistente
        String accion = "banear";
        String typeUser = "alumno";

        // Llamar al método (no se espera que devuelva nada)
        cambiarEstadoCuentaService.cambiarEstadoCuenta(userId, accion, typeUser);

        Alumno alumno = alumnoDAO.obtenerAlumnoPorId(userId);
        assertNull(alumno);
    }

    @Test
    void testAccionNoValida() {
        String userId = "3";
        String accion = "invalid"; // Acción no válida
        String typeUser = "alumno";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cambiarEstadoCuentaService.cambiarEstadoCuenta(userId, accion, typeUser);
        });

        assertEquals("Acción no válida: invalid", exception.getMessage());
    }

    @Test
    public void testBanearAdministrador() {
        String userId = "789";
        String accion = "invalid";
        String typeUser = "admin";

        // Verificar que se lanza la excepción correcta
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cambiarEstadoCuentaService.cambiarEstadoCuenta(userId, accion, typeUser);
        });

        assertEquals("Acción no válida: invalid", exception.getMessage());
    }

}
