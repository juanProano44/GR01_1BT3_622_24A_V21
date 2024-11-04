package com.example.service;

import com.example.dao.RespuestaEncuestaDAO;
import com.example.model.RespuestaEncuesta;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RespuestaEncuestaDAOTest {

    private RespuestaEncuestaDAO respuestaEncuestaDAO;
    private RespuestaEncuesta respuesta;
    private final int idSolicitudTest = 1;

    @BeforeEach
    public void setUp() {
        // Inicializar el DAO y la respuesta de prueba
        respuestaEncuestaDAO = new RespuestaEncuestaDAO();

        // Crear una RespuestaEncuesta para las pruebas
        respuesta = new RespuestaEncuesta();
        respuesta.setAlumnoId(123);
        respuesta.setTutorId(456);
        respuesta.setSolicitudId(idSolicitudTest);
        respuesta.setCodigomateria(789);
        respuesta.setCalificacion(5);

        // Guardar la respuesta en la base de datos
        respuestaEncuestaDAO.saveRespuesta(respuesta);
    }

    @AfterEach
    public void tearDown() {
        // Limpiar la base de datos después de cada prueba
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            RespuestaEncuesta respuestaGuardada = session.get(RespuestaEncuesta.class, respuesta.getId());
            if (respuestaGuardada != null) {
                session.delete(respuestaGuardada);
            }
            session.getTransaction().commit();
        }
    }

    @Test
    public void testSaveRespuesta() {
        // Verificar que la respuesta se ha guardado correctamente en la base de datos
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            RespuestaEncuesta savedRespuesta = session.get(RespuestaEncuesta.class, respuesta.getId());
            assertNotNull(savedRespuesta, "La respuesta debe estar guardada en la base de datos");
            assertEquals(123, savedRespuesta.getAlumnoId(), "El ID de alumno debe coincidir");
            assertEquals(456, savedRespuesta.getTutorId(), "El ID de tutor debe coincidir");
            assertEquals(idSolicitudTest, savedRespuesta.getSolicitudId(), "El ID de solicitud debe coincidir");
            assertEquals(789, savedRespuesta.getCodigomateria(), "El código de materia debe coincidir");
            assertEquals(5, savedRespuesta.getCalificacion(), "La calificación debe coincidir");
        }
    }

    @Test
    public void testGetRespuestasPorSolicitud() {
        // Ejecutar el método para obtener respuestas por solicitud
        List<RespuestaEncuesta> respuestas = respuestaEncuestaDAO.getRespuestasPorSolicitud(idSolicitudTest);

        // Verificar que la lista de respuestas obtenida no es nula y contiene al menos una respuesta
        assertNotNull(respuestas, "La lista de respuestas no debe ser nula");
        assertFalse(respuestas.isEmpty(), "La lista de respuestas no debe estar vacía");

        // Verificar que la respuesta tiene los valores esperados
        RespuestaEncuesta retrievedRespuesta = respuestas.stream()
                .filter(r -> r.getAlumnoId() == 123 &&
                        r.getTutorId() == 456 &&
                        r.getSolicitudId() == idSolicitudTest &&
                        r.getCodigomateria() == 789 &&
                        r.getCalificacion() == 5)
                .findFirst()
                .orElse(null);

        assertNotNull(retrievedRespuesta, "La lista debe contener la respuesta con los valores esperados");
    }
}