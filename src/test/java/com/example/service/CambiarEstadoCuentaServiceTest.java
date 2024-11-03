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
        String email = "testemail@epn.edu.ec";
        String accionBanear = "banear";
        String accionEliminar = "eliminar";
        String typeUser = "admin";

        // Comprobar que el administrador no existe
        Administrador existingAdmin = administradorDAO.findByEmail(email);
        if (existingAdmin != null) {
            cambiarEstadoCuentaService.cambiarEstadoCuenta(String.valueOf(existingAdmin.getId()), accionEliminar, typeUser);
        }

        // Crear un nuevo administrador
        Administrador admin = new Administrador();
        admin.setEmail(email);
        admin.setEstadoCuenta("activo");
        administradorDAO.registrarAdmin(admin);

        // Banea al administrador
        cambiarEstadoCuentaService.cambiarEstadoCuenta(String.valueOf(admin.getId()), accionBanear, typeUser);

        // Verificar que el administrador está baneado
        admin = administradorDAO.findByEmail(email);
        assertEquals("baneado", admin.getEstadoCuenta());

        // Borrar al administrador de la BDD para evitar sobrecarga de datos
        cambiarEstadoCuentaService.cambiarEstadoCuenta(String.valueOf(admin.getId()), accionEliminar, typeUser);

        // Verificar que el administrador fue eliminado
        admin = administradorDAO.findByEmail(email);
        assertNull(admin);
    }


}
