package com.example.dao;

import com.example.model.Alumno;
import com.example.model.Tutor;
import com.example.model.Tutoria;
import com.example.utils.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class TutorDAO {

    private final SessionFactory sessionFactory;

    public TutorDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory(); // Inicializa sessionFactory en el constructor
    }

    public void registrarTutor(Tutor tutor) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(tutor);
            transaction.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Tutor findByEmail(String email) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("FROM Tutor WHERE email = :email", Tutor.class)
                    .setParameter("email", email)
                    .uniqueResult(); // Devuelve un tutor si lo encuentra, sino null
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }
    public List<Tutor> getAllTutoresWithDetails() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            // Primera consulta para cargar solo las tutorías (sin solicitudes)
            List<Tutor> tutores = session.createQuery(
                    "SELECT DISTINCT t FROM Tutor t " +
                            "LEFT JOIN FETCH t.tutorias", Tutor.class).getResultList();

            // Segunda consulta para inicializar las solicitudes (separada para evitar MultipleBagFetchException)
            for (Tutor tutor : tutores) {
                for (Tutoria tutoria : tutor.getTutorias()) {
                    Hibernate.initialize(tutoria.getSolicitudes());
                }
            }

            session.getTransaction().commit();
            return tutores;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    // Cambiar el estado del alumno (por ejemplo, a "baneado")
    public void cambiarEstado(String tutorId, String nuevoEstado) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Tutor tutor = session.get(Tutor.class, Integer.parseInt(tutorId));
        if (tutor != null) {
            tutor.setEstadoCuenta(nuevoEstado);
            session.update(tutor);
        }

        session.getTransaction().commit();
        session.close();
    }

    // Eliminar un alumno por su ID
    public void eliminarTutor(String tutorId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Tutor tutor = session.get(Tutor.class, Integer.parseInt(tutorId));
        if (tutor != null) {
            session.delete(tutor);
        }

        session.getTransaction().commit();
        session.close();
    }

    // Método para obtener un Tutor por su ID
    public Tutor obtenerTutorPorId(String tutorId) {
        Session session = null;
        Tutor tutor = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            // Obtener el tutor por su ID
            tutor = session.get(Tutor.class, Integer.parseInt(tutorId));

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

        return tutor;
    }

}
