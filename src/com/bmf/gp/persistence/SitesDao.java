package com.bmf.gp.persistence;

import com.bmf.gp.entity.SitesEntity;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Set;

/**
 * Created by Michael on 4/3/2016.
 */
public class SitesDao {
    private final Logger log = Logger.getLogger(this.getClass());

    public Set<SitesEntity> getAllSites() {
        Set<SitesEntity> sites;
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        sites = (Set<SitesEntity>)session.createCriteria
                (SitesEntity
                        .class);
        return sites;
    }

    public SitesEntity getSite(Integer id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        return (SitesEntity)session.get(SitesEntity.class, id);
    }

    public SitesEntity getSiteByKey(String key) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();

        Criteria crit = session.createCriteria(SitesEntity.class);
        crit.add( Restrictions.eq("siteKey",key) );
        List<SitesEntity> sites = crit.list();

        return sites.get(0);
    }

    public void updateSite(SitesEntity site) {

        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.merge(site);
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

    public Boolean deleteSite(SitesEntity site) {

        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction dbTransaction = null;

        try {
            dbTransaction = session.beginTransaction();
            SitesEntity siteToDelete = (SitesEntity) session.get(SitesEntity.class, site.getSiteId());
            session.delete(siteToDelete);
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

    public int addSite(SitesEntity site) {

        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction tx = null;
        Integer siteId = null;
        try {
            tx = session.beginTransaction();
            siteId = (Integer) session.save(site);
            tx.commit();
            log.info("Added site: " + siteId);
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            log.error(e);
        } finally {
            session.close();
        }
        return siteId;
    }
}
