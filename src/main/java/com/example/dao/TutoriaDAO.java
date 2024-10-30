package com.example.dao;

import com.example.model.Tutoria;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query; // Importar la clase Query de Hibernate

import java.util.List;

public class TutoriaDAO {

    private final SessionFactory sessionFactory;

    public TutoriaDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory(); // Inicializa sessionFactory en el constructor
    }

    // Obtener las tutorías no aceptadas por un alumno específico
    public List<Tutoria> buscarTutoriasDisponibles(int alumnoId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tutoria> query = session.createQuery(
                    "FROM Tutoria t WHERE t.id NOT IN (SELECT s.tutoria.id FROM Solicitud s WHERE s.alumno.id = :alumnoId)",
                    Tutoria.class);
            query.setParameter("alumnoId", alumnoId);
            return query.getResultList();
        }
    }

    // Obtener todas las tutorías
    public List<Tutoria> getAllTutorias() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Tutoria", Tutoria.class).list();
        }
    }

    // Guardar o actualizar una tutoría
    public void reguistarTutoria(Tutoria tutoria) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(tutoria);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    // Obtener una tutoría por su ID
    public Tutoria getTutoriaById(int tutoriaId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Tutoria.class, tutoriaId);
        }
    }

    // Obtener las tutorías por el ID del tutor
    public List<Tutoria> listarSolicitudesPendientes(int tutorId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Tutoria t WHERE t.tutor.id = :tutorId", Tutoria.class)
                    .setParameter("tutorId", tutorId)
                    .list();
        }
    }


}
