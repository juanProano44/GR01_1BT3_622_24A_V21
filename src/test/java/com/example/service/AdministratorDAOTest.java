package com.example.service;

import com.example.dao.AdministratorDAO;
import com.example.model.Administrador;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AdministratorDAOTest {

    private AdministratorDAO administratorDAO;
    private String adminEmail = "admin1@example.com"; // Correo del administrador de prueba

    @BeforeEach
    void setUp() {
        administratorDAO = new AdministratorDAO();
        // Registrar un administrador en la base de datos para la prueba
        Administrador admin = new Administrador();
        admin.setEmail(adminEmail);
        admin.setEstadoCuenta("activo"); // Asignar un estado inicial
        administratorDAO.registrarAdmin(admin);
    }

    @ParameterizedTest
    @ValueSource(strings = {"admin1@example.com", "admin2@example.com"})
    void testFindByEmail(String email) {
        Administrador admin = administratorDAO.findByEmail(email);
        if (email.equals(adminEmail)) {
            assertNotNull(admin, "El administrador debería existir para el correo: " + email);
            assertEquals(email, admin.getEmail(), "El correo debería coincidir");
        } else {
            assertNull(admin, "No debería existir administrador para el correo: " + email);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1, activo",
            "1, inactivo"
    })
    void testCambiarEstado(String adminId, String nuevoEstado) {
        administratorDAO.cambiarEstado(adminId, nuevoEstado);
        Administrador admin = administratorDAO.findByEmail(adminEmail);
        assertNotNull(admin, "El administrador debería existir");
        assertEquals(nuevoEstado, admin.getEstadoCuenta(), "El estado debería ser: " + nuevoEstado);
    }

    @AfterEach
    void tearDown() {
        // Eliminar el administrador creado durante la prueba
        Administrador admin = administratorDAO.findByEmail(adminEmail);
        if (admin != null) {
            administratorDAO.eliminarAdmin(String.valueOf(admin.getId()));         }
    }
}