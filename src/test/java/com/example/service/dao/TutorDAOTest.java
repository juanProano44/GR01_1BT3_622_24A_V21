package com.example.service.dao;

import com.example.dao.TutorDAO;
import com.example.model.Tutor;
import com.example.service.CambiarEstadoCuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static junit.framework.Assert.assertNull;

public class TutorDAOTest {
    private CambiarEstadoCuentaService cambiarEstadoCuentaService;
    private TutorDAO tutorDAO = new TutorDAO();



    @BeforeEach
    void setUp() {
        cambiarEstadoCuentaService = new CambiarEstadoCuentaService();
    }

    @Test
    void testEliminarTutor() {
        String userId = "2";
        String action = "eliminar";
        String typeUser = "tutor";

        cambiarEstadoCuentaService.cambiarEstadoCuenta(userId, action, typeUser);

        //Verificar que el tutor fue eliminado
        Tutor tutor = tutorDAO.obtenerTutorPorId(userId);
        assertNull(tutor);


    }
}
