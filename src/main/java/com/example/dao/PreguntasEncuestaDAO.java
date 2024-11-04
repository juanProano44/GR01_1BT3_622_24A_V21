package com.example.dao;

import com.example.model.PreguntasEncuesta;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.util.List;

public class PreguntasEncuestaDAO {

    private final SessionFactory factory;

    public PreguntasEncuestaDAO() {
        this.factory = HibernateUtil.getSessionFactory();
    }

    // Método para guardar una nueva pregunta
    @Transactional
    public void savePregunta(PreguntasEncuesta pregunta) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.save(pregunta);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    public List<PreguntasEncuesta> getPreguntasByMateria(int codigomateria) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            List<PreguntasEncuesta> preguntas = session.createQuery(
                            "FROM PreguntasEncuesta p WHERE p.materia.codigomateria = :codigomateria", PreguntasEncuesta.class)
                    .setParameter("codigomateria", codigomateria)
                    .getResultList();
            session.getTransaction().commit();
            return preguntas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para obtener todas las preguntas
    public List<PreguntasEncuesta> getAllPreguntas() {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            List<PreguntasEncuesta> preguntas = session.createQuery("from PreguntasEncuesta", PreguntasEncuesta.class).getResultList();
            session.getTransaction().commit();
            return preguntas;
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    // Método para obtener una pregunta por su ID
    public PreguntasEncuesta getPreguntaById(int id) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            PreguntasEncuesta pregunta = session.get(PreguntasEncuesta.class, id);
            session.getTransaction().commit();
            return pregunta;
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    // Método para eliminar una pregunta por su ID
    public void deletePregunta(int id) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            PreguntasEncuesta pregunta = session.get(PreguntasEncuesta.class, id);
            if (pregunta != null) {
                session.delete(pregunta);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
