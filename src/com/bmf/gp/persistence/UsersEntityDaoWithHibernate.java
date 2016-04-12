package com.bmf.gp.persistence;

import com.bmf.gp.entity.UsersEntity;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felic on 2/7/2016.
 */
public class UsersEntityDaoWithHibernate implements UsersEntityDao {

    private final Logger log = Logger.getLogger(this.getClass());

    public List<UsersEntity> getAllUsers() {
        List<UsersEntity> users = new ArrayList<UsersEntity>();
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        users = (ArrayList<UsersEntity>)session.createCriteria(UsersEntity.class).list();
        return users;
    }

    public UsersEntity getUser(Integer id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        return (UsersEntity)session.get(UsersEntity.class, id);
    }

    public void updateUser(UsersEntity user) {

        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Boolean deleteUser(UsersEntity user) {

        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction dbTransaction = null;

        try {
            dbTransaction = session.beginTransaction();
            UsersEntity userToDelete = (UsersEntity)session.get(UsersEntity.class, user.getUserId());
            session.delete(userToDelete);
            dbTransaction.commit();

        } catch (HibernateException error) {
            if (dbTransaction != null) dbTransaction.rollback();
            error.printStackTrace();
            return false;
        } finally {
            session.close();
        }

        return true;

    }

    public int addUser(UsersEntity user) {

        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction tx = null;
        Integer userId = null;
        try {
            tx = session.beginTransaction();
            userId = (Integer) session.save(user);
            tx.commit();
            log.info("Added user: " + user + " with id of: " + userId);
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            log.error(e);
        } finally {
            session.close();
        }
        return userId;
    }
}