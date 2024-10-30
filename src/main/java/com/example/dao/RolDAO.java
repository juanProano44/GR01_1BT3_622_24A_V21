package com.example.dao;

import com.example.model.Materia;
import com.example.model.Rol;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class RolDAO {

    private final SessionFactory sessionFactory;

    public RolDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Rol> getAllRols() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("from Rol").list();
        }
    }


}
