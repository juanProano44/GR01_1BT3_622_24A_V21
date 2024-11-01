package com.example.dao;

import com.example.model.Administrador;
import com.example.model.Alumno;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AdministratorDAO {

    private final SessionFactory sessionFactory;

    public AdministratorDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory(); // Inicializa sessionFactory en el constructor
    }


    public void registrarAdmin(Administrador admin) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(admin);
            transaction.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Administrador findByEmail(String email) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("FROM Administrador WHERE email = :email", Administrador.class)
                    .setParameter("email", email)
                    .uniqueResult(); // Devuelve un admin si lo encuentra, sino null
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public Administrador findByID(String id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("FROM Administrador WHERE id = :id", Administrador.class)
                    .setParameter("id", id)
                    .uniqueResult(); // Devuelve un admin si lo encuentra, sino null
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public List<Administrador> getAllAdministradores() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Administrador", Administrador.class).list();
        }
    }

    public void cambiarEstado(String adminId, String nuevoEstado) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Administrador admin = session.get(Administrador.class, Integer.parseInt(adminId));
        if (admin != null) {
            admin.setEstadoCuenta(nuevoEstado);
            session.update(admin);
        }

        session.getTransaction().commit();
        session.close();
    }


    public void eliminarAdmin(String adminId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Administrador admin = session.get(Administrador.class, Integer.parseInt(adminId));
        if (admin != null) {
            session.delete(admin);
        }

        session.getTransaction().commit();
        session.close();
    }

}
