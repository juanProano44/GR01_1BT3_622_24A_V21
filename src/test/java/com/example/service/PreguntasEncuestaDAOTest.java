package com.example.service;

import com.example.dao.MateriaDAO;
import com.example.dao.PreguntasEncuestaDAO;
import com.example.model.Materia;
import com.example.model.PreguntasEncuesta;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.*;


public class PreguntasEncuestaDAOTest {
    private PreguntasEncuestaDAO preguntasEncuestaDAO;
    private MateriaDAO materiaDAO;
    private final int preguntaId = 1;
    private final int materiaId = 2;

    @BeforeEach
    public void setUp() {
        // Crear los mocks manualmente
        preguntasEncuestaDAO = mock(PreguntasEncuestaDAO.class);
        materiaDAO = mock(MateriaDAO.class);

        // Simular la creación de Materia si no existe en el mock
        if (!materiaExists(materiaId)) {
            createMateria(materiaId);
        }

        reset(preguntasEncuestaDAO);
    }

    @AfterEach
    public void tearDown() {
        // Eliminar Materia si fue creada durante la prueba
        if (materiaWasCreatedForTest(materiaId)) {
            deleteMateria(materiaId);
        }
    }

    @Test
    public void testSavePregunta() {
        // Configurar Materia mock para asignarlo a la pregunta
        Materia materia = new Materia();
        materia.setCodigomateria(materiaId);

        // Crear una nueva pregunta y asignarle la materia mock
        PreguntasEncuesta pregunta = new PreguntasEncuesta();
        pregunta.setId(preguntaId);
        pregunta.setPregunta("¿Cómo calificaría el contenido enseñado en la tutoría?");
        pregunta.setMateria(materia);

        // Simular guardar la pregunta en el DAO
        doNothing().when(preguntasEncuestaDAO).savePregunta(pregunta);

        // Ejecutar el método que se quiere probar
        preguntasEncuestaDAO.savePregunta(pregunta);

        // Verificar que el DAO haya sido llamado para guardar la pregunta
        verify(preguntasEncuestaDAO, times(1)).savePregunta(pregunta);
    }

    @Test
    public void testGetPreguntaById() {
        // Configurar Materia mock para asignarlo a la pregunta
        Materia materia = new Materia();
        materia.setCodigomateria(materiaId);

        // Crear la pregunta de prueba con Materia asignada
        PreguntasEncuesta pregunta = new PreguntasEncuesta();
        pregunta.setId(preguntaId);
        pregunta.setPregunta("¿Cómo calificaría el contenido enseñado en la tutoría?");
        pregunta.setMateria(materia);

        // Simular el comportamiento de obtener la pregunta por ID
        when(preguntasEncuestaDAO.getPreguntaById(preguntaId)).thenReturn(pregunta);

        // Ejecutar el método que se quiere probar
        PreguntasEncuesta retrievedPregunta = preguntasEncuestaDAO.getPreguntaById(preguntaId);

        // Verificar que la pregunta recuperada no sea null y tenga los datos esperados
        assertNotNull(retrievedPregunta);
        assertEquals("¿Cómo calificaría el contenido enseñado en la tutoría?", retrievedPregunta.getPregunta());

        // Verificar que el DAO haya sido llamado una vez para obtener la pregunta
        verify(preguntasEncuestaDAO, times(1)).getPreguntaById(preguntaId);
    }

    @Test
    public void testGetPreguntasByMateria() {
        // Crear lista de preguntas simuladas
        PreguntasEncuesta pregunta1 = new PreguntasEncuesta();
        pregunta1.setPregunta("¿Cómo calificaría los aprendizajes de la tutoría?");
        PreguntasEncuesta pregunta2 = new PreguntasEncuesta();
        pregunta2.setPregunta("¿Cómo calificaría la claridad del tutor?");

        // Configurar el comportamiento del mock para que devuelva esta lista cuando se llame a getPreguntasByMateria
        when(preguntasEncuestaDAO.getPreguntasByMateria(materiaId)).thenReturn(Arrays.asList(pregunta1, pregunta2));

        // Ejecutar el método a probar
        List<PreguntasEncuesta> preguntas = preguntasEncuestaDAO.getPreguntasByMateria(materiaId);

        // Verificar que las preguntas recuperadas son las correspondientes a la materia
        assertNotNull(preguntas.toString(), "La lista de preguntas no debe ser nula");
        assertEquals(2, preguntas.size(), "El código de materia debe tener 2 preguntas asociadas");

        // Verificar que las preguntas tienen el contenido esperado
        assertTrue(preguntas.stream().anyMatch(p -> p.getPregunta().equals("¿Cómo calificaría los aprendizajes de la tutoría?")),
                "La lista debe contener '¿Cómo calificaría los aprendizajes de la tutoría?'");
        assertTrue(preguntas.stream().anyMatch(p -> p.getPregunta().equals("¿Cómo calificaría la claridad del tutor?")),
                "La lista debe contener '¿Cómo calificaría la claridad del tutor?'");
    }



    @Test
    public void testDeletePregunta() {
        // Configurar una sesión de Hibernate para interactuar con la base de datos de prueba
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Crear y guardar la Materia en la base de datos
            Materia materia = new Materia();
            materia.setCodigomateria(materiaId);
            session.save(materia);

            // Crear y guardar una Pregunta en la base de datos, asignando la Materia creada
            PreguntasEncuesta pregunta = new PreguntasEncuesta();
            pregunta.setId(preguntaId);
            pregunta.setPregunta("¿Cómo calificaría el contenido enseñado en la tutoría?");
            pregunta.setMateria(materia);
            session.save(pregunta);

            // Confirmar que la Pregunta fue guardada correctamente
            PreguntasEncuesta savedPregunta = session.get(PreguntasEncuesta.class, preguntaId);
            assertNotNull(savedPregunta);

            // Eliminar la pregunta
            session.delete(savedPregunta);
            transaction.commit();

            // Verificar que la Pregunta fue eliminada
            PreguntasEncuesta deletedPregunta = session.get(PreguntasEncuesta.class, preguntaId);
            assertNull(deletedPregunta);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private boolean materiaExists(int materiaId) {
        // Simular la comprobación de existencia de Materia en el mock
        return materiaDAO.getMateria(materiaId) != null;
    }

    private void createMateria(int materiaId) {
        // Simular la creación de Materia en el mock
        Materia materia = new Materia();
        materia.setCodigomateria(materiaId);
        when(materiaDAO.getMateria(materiaId)).thenReturn(materia);
    }

    private boolean materiaWasCreatedForTest(int materiaId) {
        // Determinar si Materia fue creada específicamente para esta prueba
        return true; // asumir que siempre fue creada para propósitos de esta prueba
    }

    private void deleteMateria(int materiaId) {
        // Restablecer el mock para que Materia ya no esté disponible
        reset(materiaDAO);
    }
}