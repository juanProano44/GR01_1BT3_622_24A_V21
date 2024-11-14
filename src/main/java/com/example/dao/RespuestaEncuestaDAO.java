package com.example.dao;

import com.example.model.RespuestaEncuesta;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class RespuestaEncuestaDAO {

    private final SessionFactory factory;

    public RespuestaEncuestaDAO() {
        this.factory = HibernateUtil.getSessionFactory();
    }

    public void saveRespuesta(RespuestaEncuesta respuesta) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            // Verificar y depurar el estado del objeto antes de guardarlo
            if (respuesta.getAlumno() == null) {
                System.out.println("Alumno es null en RespuestaEncuesta.");
            } else {
                System.out.println("Alumno ID: " + respuesta.getAlumno().getId());
            }

            session.save(respuesta);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<RespuestaEncuesta> getRespuestasPorSolicitud(int idSolicitud) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM RespuestaEncuesta r WHERE r.solicitud.id = :idSolicitud";
            return session.createQuery(hql, RespuestaEncuesta.class)
                    .setParameter("idSolicitud", idSolicitud)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // Método para verificar si ya existe una respuesta para una solicitud específica
    public boolean existeRespuestaParaSolicitud(int idSolicitud) {
        try (Session session = factory.openSession()) {
            String hql = "SELECT COUNT(r) FROM RespuestaEncuesta r WHERE r.solicitud.id = :idSolicitud";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("idSolicitud", idSolicitud)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
