package com.example.service;

import com.example.dao.AdministratorDAO;
import com.example.dao.AlumnoDAO;
import com.example.dao.TutorDAO;
import com.example.model.Administrador;
import com.example.model.Alumno;
import com.example.model.Tutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CambiarEstadoCuentaServiceTest {

    private CambiarEstadoCuentaService cambiarEstadoCuentaService;
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private TutorDAO tutorDAO = new TutorDAO();
    private AdministratorDAO administradorDAO = new AdministratorDAO();
    private Administrador admin = new Administrador();


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
        String accionBanear = "banear";
        String accionEliminar = "eliminar";
        String typeUser = "admin";

        // Create an admin with userId 789
        admin.setId(userId);
        admin.setEstadoCuenta("activo");
        administradorDAO.registrarAdmin(admin);

        // Ban the admin
        cambiarEstadoCuentaService.cambiarEstadoCuenta(userId, accionBanear, typeUser);

        // Verify the admin is banned
        admin = administradorDAO.findByID(userId);
        assertEquals("baneado", admin.getEstadoCuenta());

        // Delete the admin from the database
        cambiarEstadoCuentaService.cambiarEstadoCuenta(userId, accionEliminar, typeUser);

        // Verify the admin is deleted
        admin = administradorDAO.findByID(userId);
        assertNull(admin);
    }

}
