package com.example.service;

import com.example.dao.SolicitudDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;


public class AceptarTutoriaServiceTest {
    private SolicitudDAO solicitudDAO;
    private AceptarTutoriaService aceptarTutoriaService;

    @BeforeEach
    public void setUp() {
        solicitudDAO = mock(SolicitudDAO.class);
        aceptarTutoriaService = new AceptarTutoriaService(solicitudDAO);
    }

    @Test
    public void testAceptarTutoria() throws Exception {
        // Arrange
        int tutoriaId = 1;

        // Act
        aceptarTutoriaService.aceptarTutoria(tutoriaId);

    }
}