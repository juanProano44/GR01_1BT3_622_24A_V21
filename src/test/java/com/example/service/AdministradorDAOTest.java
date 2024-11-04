package com.example.service;
import com.example.dao.AdministratorDAO;
import com.example.model.Administrador;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class AdministradorDAOTest {

    private AdministratorDAO administratorDAO;
    private String adminEmail = "admin1@example.com"; // Correo del administrador de prueba

    // Variables para pruebas parametrizadas
    private String email;
    //private String adminId;
    private String nuevoEstado;

    // Constructor para las pruebas parametrizadas
    public AdministradorDAOTest(String email, String nuevoEstado) {
        this.email = email;
        this.nuevoEstado = nuevoEstado;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"admin1@example.com", "activo"},
                {"admin2@example.com",  "inactivo"}
        });
    }

    @Before
    public void setUp() {
        administratorDAO = new AdministratorDAO();
        // Registrar un administrador en la base de datos para la prueba
        Administrador admin = new Administrador();
        admin.setEmail(adminEmail);
        admin.setEstadoCuenta("activo"); // Asignar un estado inicial
        administratorDAO.registrarAdmin(admin);
    }

    @Test
    public void testFindByEmail() {
        Administrador admin = administratorDAO.findByEmail(email);
        if (email.equals(adminEmail)) {
            assertNotNull("El administrador debería existir para el correo: " + email, admin);
            assertEquals("El correo debería coincidir", email, admin.getEmail());
        } else {
            assertNull("No debería existir administrador para el correo: " + email, admin);
        }
    }

    @Test
    public void testCambiarEstado() {
        Administrador adminDao = administratorDAO.findByEmail(adminEmail);
        administratorDAO.cambiarEstado(String.valueOf(adminDao.getId()), nuevoEstado);
        Administrador admin = administratorDAO.findByEmail(adminEmail);
        assertNotNull("El administrador debería existir", admin);
        assertEquals("El estado debería ser: " + nuevoEstado, nuevoEstado, admin.getEstadoCuenta());
    }
    @After
    public void tearDown() {
        // Eliminar el administrador creado durante la prueba
        Administrador admin = administratorDAO.findByEmail(adminEmail);
        if (admin != null) {
            administratorDAO.eliminarAdmin(String.valueOf(admin.getId()));
        }
    }

}