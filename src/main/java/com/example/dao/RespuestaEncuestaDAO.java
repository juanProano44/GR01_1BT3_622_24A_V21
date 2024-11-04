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
            session.save(respuesta);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<RespuestaEncuesta> getRespuestasPorSolicitud(int idSolicitud) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM RespuestaEncuesta r WHERE r.tutoria.id = :idSolicitud";
            return session.createQuery(hql, RespuestaEncuesta.class)
                    .setParameter("idSolicitud", idSolicitud)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
