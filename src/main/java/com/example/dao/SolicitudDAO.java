package com.example.dao;

import com.example.model.Solicitud;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.example.utils.HibernateUtil;

import java.util.Collections;
import java.util.List;

public class SolicitudDAO {

    public void solicitarTutoria(Solicitud solicitud) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // Guardar la solicitud
            session.save(solicitud);
            desplegarConfirmacion(solicitud);
            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public int contarSolicitudesAceptadasPorTutoria(int tutoriaId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(s) FROM Solicitud s WHERE s.tutoria.id = :tutoriaId AND s.estado = 'Aceptada'";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("tutoriaId", tutoriaId)
                    .uniqueResult();
            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // En SolicitudDAO
    public List<Solicitud> getSolicitudesPorEstadoYtutor(int tutorId, String estado) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Solicitud s WHERE s.tutoria.tutor.id = :tutorId AND s.estado = :estado";
            return session.createQuery(hql, Solicitud.class)
                    .setParameter("tutorId", tutorId)
                    .setParameter("estado", estado)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    private void desplegarConfirmacion(Solicitud solicitud) {
        System.out.println("Solicitud guardada correctamente: " + solicitud.getId());
    }
    // Método para obtener una solicitud por su ID
    public Solicitud getSolicitudById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Solicitud.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    // Método para obtener todas las solicitudes de un alumno, sin importar el estado
    public List<Solicitud> getSolicitudesPorAlumno(int alumnoId) {
        Transaction transaction = null;
        List<Solicitud> solicitudes = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Consulta HQL para obtener todas las solicitudes del alumno
            String hql = "FROM Solicitud s WHERE s.alumno.id = :alumnoId";
            solicitudes = session.createQuery(hql, Solicitud.class)
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
    // Obtener todas las solicitudes de un tutor (para las tutorías que ha creado)
    public List<Solicitud> getSolicitudesPorTutor(int tutorId) {
        Transaction transaction = null;
        List<Solicitud> solicitudes = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // HQL para obtener las solicitudes de las tutorías creadas por el tutor
            String hql = "FROM Solicitud s WHERE s.tutoria.tutor.id = :tutorId";
            solicitudes = session.createQuery(hql, Solicitud.class)
                    .setParameter("tutorId", tutorId)
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

    // Método refactorizado para obtener una solicitud por su ID
    public Solicitud getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Solicitud.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para actualizar una solicitud
    public void responderSolicitud(Solicitud solicitud) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(solicitud); // Actualizar la solicitud
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public Solicitud findById(int id) {
        Transaction transaction = null;
        Solicitud solicitud = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            solicitud = session.get(Solicitud.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return solicitud;
    }
    public void eliminarSolicitud(int idSolicitud) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Solicitud solicitud = session.get(Solicitud.class, idSolicitud);
            if (solicitud != null) {
                session.delete(solicitud);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
