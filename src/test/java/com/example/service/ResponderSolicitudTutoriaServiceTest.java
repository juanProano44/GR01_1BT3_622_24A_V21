package com.example.service;

import com.example.model.SolicitudTutoria;
import com.example.dao.SolicitudTutoriaDAO;
import com.example.service.ResponderSolicitudTutoriaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ResponderSolicitudTutoriaServiceTest {

    private final int solicitudId;
    private final String accion;
    private final String estadoInicial;
    private final String estadoEsperado;

    public ResponderSolicitudTutoriaServiceTest(int solicitudId, String accion, String estadoInicial, String estadoEsperado) {
        this.solicitudId = solicitudId;
        this.accion = accion;
        this.estadoInicial = estadoInicial;
        this.estadoEsperado = estadoEsperado;
    }

    // Definimos los parámetros de prueba
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {1, "aceptar", "aceptada", "aceptada"},   // No cambiar el estado si ya está aceptada
                {2, "rechazar", "rechazada", "rechazada"}, // No cambiar el estado si ya está rechazada
                {3, "aceptar", "pendiente", "aceptada"},  // Cambiar de pendiente a aceptada
                {4, "rechazar", "pendiente", "rechazada"} // Cambiar de pendiente a rechazada
        });
    }

    @Test
    public void testResponderSolicitud() {
        // Simulamos el comportamiento del DAO usando el estadoInicial pasado por los parámetros
        SolicitudTutoriaDAO solicitudTutoriaDAO = new SolicitudTutoriaDAO() {
            @Override
            public SolicitudTutoria getById(int id) {
                SolicitudTutoria solicitud = new SolicitudTutoria();
                solicitud.setId(id);
                solicitud.setEstado(estadoInicial);
                return solicitud;
            }
            @Override
            public void update(SolicitudTutoria solicitud) {
                // Verificar que el estado de la solicitud sea el esperado según los parámetros
                assertEquals(estadoEsperado, solicitud.getEstado());
            }
        };

        ResponderSolicitudTutoriaService service = new ResponderSolicitudTutoriaService(solicitudTutoriaDAO);
        // Ejecutar el método bajo prueba usando los parámetros solicitudId y accion
        service.responderSolicitud(solicitudId, accion);
    }


}
