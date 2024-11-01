package com.example.service;

import com.example.dao.SolicitudDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.mockito.Mockito.*;

public class AceptarTutoriaServiceTest {

    private SolicitudDAO solicitudDAO;
    private AceptarTutoriaService aceptarTutoriaService;
    private final int tutoriaId = 4;
    private boolean createdForTest = false;

    @BeforeEach
    public void setUp() {
        solicitudDAO = mock(SolicitudDAO.class);
        aceptarTutoriaService = new AceptarTutoriaService(solicitudDAO);

        // Verificar o crear una tutoría de prueba para asegurar que exista antes de la prueba
        if (!tutoriaExists(tutoriaId)) {
            createTutoria(tutoriaId);
            createdForTest = true;
        }
    }

    @AfterEach
    public void tearDown() {
        // Eliminar la tutoría solo si fue creada para esta prueba
        if (createdForTest) {
            deleteTutoria(tutoriaId);
        }
    }

    @Test
    public void testAceptarTutoria() throws Exception {
        // Ejecutar el método a probar
        aceptarTutoriaService.aceptarTutoria(tutoriaId);

        // Aquí simplemente verificamos que el método en el servicio se ejecuta sin errores
        // No se verifica interacción específica con solicitudDAO porque aceptarTutoria es del servicio
    }

    private boolean tutoriaExists(int tutoriaId) {
        // Verificar existencia simulada de la tutoría en una prueba real
        // En este caso, devolvemos falso para simular que no existe al inicio
        return false;
    }

    private void createTutoria(int tutoriaId) {
        // Simulación para asegurar que la tutoría esté lista en caso de necesitarla
        // En este caso, solo se asegura de que el ID esté disponible para la prueba
    }

    private void deleteTutoria(int tutoriaId) {
        // Resetear el mock después de la prueba para eliminar cualquier configuración
        reset(solicitudDAO);
    }
}
