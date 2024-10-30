package com.example.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.example.dao.TutoriaDAO;
import com.example.dao.MateriaDAO;
import com.example.model.Materia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class RegistrarTutoriaServiceTest {

    private TutoriaDAO tutoriaDAO;
    private MateriaDAO materiaDAO;
    private RegistrarTutoriaService registrarTutoriaService;

    @BeforeEach
    public void setUp() {
        // Crear los mocks manualmente
        tutoriaDAO = mock(TutoriaDAO.class);
        materiaDAO = mock(MateriaDAO.class);

        // Pasar los mocks al constructor del servicio
        registrarTutoriaService = new RegistrarTutoriaService(tutoriaDAO, materiaDAO);
    }


    @Test
    public void testObtenerMateriasPorTutor() {
        // Se simula el comportamiento del DAO
        when(materiaDAO.getMateriasByTutorId(1)).thenReturn(Arrays.asList(new Materia(), new Materia()));

        // Método que se quiere probar
        List<Materia> materias = registrarTutoriaService.obtenerMateriasPorTutor(1);

        // Verificar que el resultado tiene el tamaño esperado
        assertEquals(2, materias.size());

        // Verificar que el DAO haya sido llamado una vez
        verify(materiaDAO, times(1)).getMateriasByTutorId(1);
    }
}
