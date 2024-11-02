package com.example.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.example.dao.TutoriaDAO;
import com.example.dao.MateriaDAO;
import com.example.model.Materia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.Arrays;
import java.util.List;

public class RegistrarTutoriaServiceTest {

    private TutoriaDAO tutoriaDAO;
    private MateriaDAO materiaDAO;
    private RegistrarTutoriaService registrarTutoriaService;
    private final int tutorId = 1;

    @BeforeEach
    public void setUp() {
        // Crear los mocks manualmente
        tutoriaDAO = mock(TutoriaDAO.class);
        materiaDAO = mock(MateriaDAO.class);

        // Pasar los mocks al constructor del servicio
        registrarTutoriaService = new RegistrarTutoriaService(tutoriaDAO, materiaDAO);

        // Preparar el mock para que si el tutor no existe, lo cree
        if (!tutorExists(tutorId)) {
            createTutor(tutorId);
        }

        reset(materiaDAO);
    }

    @AfterEach
    public void tearDown() {
        // Eliminar el tutor si fue creado durante la prueba
        if (tutorWasCreatedForTest(tutorId)) {
            deleteTutor(tutorId);
        }
    }

    @Test
    public void testObtenerMateriasPorTutor() {
        // Se simula el comportamiento del DAO
        when(materiaDAO.getMateriasByTutorId(tutorId)).thenReturn(Arrays.asList(new Materia(), new Materia()));

        // Método que se quiere probar
        List<Materia> materias = registrarTutoriaService.obtenerMateriasPorTutor(tutorId);

        // Verificar que el resultado tiene el tamaño esperado
        assertEquals(2, materias.size());

        // Verificar que el DAO haya sido llamado una vez
        verify(materiaDAO, times(1)).getMateriasByTutorId(tutorId);
    }

    private boolean tutorExists(int tutorId) {
        // Simulación de comprobación de existencia del tutor en el DAO
        return materiaDAO.getMateriasByTutorId(tutorId) != null;
    }

    private void createTutor(int tutorId) {
        // Lógica para simular la creación de un tutor en el DAO
        when(materiaDAO.getMateriasByTutorId(tutorId)).thenReturn(Arrays.asList(new Materia(), new Materia()));
    }

    private boolean tutorWasCreatedForTest(int tutorId) {
        // Lógica para determinar si el tutor fue creado para esta prueba
        return true; // asumir que siempre fue creado para propósitos de esta prueba
    }

    private void deleteTutor(int tutorId) {
        // Lógica para simular la eliminación del tutor en el DAO
        reset(materiaDAO); // Restablece el mock para que el tutor ya no esté
    }
}
