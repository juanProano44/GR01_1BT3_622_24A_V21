package com.example.dao;

import com.example.model.SolicitudTutoria;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SolicitudTutoriaDAO {

    public static final String consultaTutoria = "from SolicitudTutoria s where s.tutoria.tutor.id = :tutorId";
    public static final String consultaTutoriaPorAlumno = "from SolicitudTutoria s where s.alumno.id = :alumnoId and s.tutoria.id = :tutoriaId";
    public static final String consultaTutoriaPorAlumnoEstado = "FROM SolicitudTutoria WHERE alumno.id = :alumnoId AND estado = 'Aceptada'";

    public List<SolicitudTutoria> getSolicitudesPorTutor(int tutorId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(consultaTutoria, SolicitudTutoria.class)
                    .setParameter("tutorId", tutorId)
                    .list();
        }
    }

    public SolicitudTutoria getSolicitudByAlumnoAndTutoria(int alumnoId, int tutoriaId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(consultaTutoriaPorAlumno,
                            SolicitudTutoria.class)
                    .setParameter("alumnoId", alumnoId)
                    .setParameter("tutoriaId", tutoriaId)
                    .uniqueResult();
        }
    }

    // Método para obtener las tutorías aceptadas por un alumno
    public List<SolicitudTutoria> getTutoriasAceptadasPorAlumno(int alumnoId) {
        Transaction transaction = null;
        List<SolicitudTutoria> solicitudes = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Consulta HQL para obtener las solicitudes de tutoría aceptadas por el alumno
            solicitudes = session.createQuery(
                            consultaTutoriaPorAlumnoEstado, SolicitudTutoria.class)
                    .setParameter("alumnoId", alumnoId)
                    .list();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return solicitudes;
    }
    public void aceptarTutoria(SolicitudTutoria solicitud) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            solicitud.setEstado("Aceptada");
            session.saveOrUpdate(solicitud);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    // Método para obtener una solicitud de tutoría por su ID
    public SolicitudTutoria getById(int solicitudId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(SolicitudTutoria.class, solicitudId); // Obtiene la solicitud por su ID
        }
    }
    public void update(SolicitudTutoria solicitud) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(solicitud); // Actualiza la solicitud de tutoría
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public void guardarSolicitud(SolicitudTutoria solicitud) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(solicitud);  // Guardar o actualizar la solicitud
            transaction.commit();  // Confirmar la transacción
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Deshacer la transacción en caso de error
            }
            e.printStackTrace();
        }
    }

}
