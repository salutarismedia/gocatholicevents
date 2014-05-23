package com.sm.gce.database;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import com.sm.gce.common.model.Adapter;
import com.sm.gce.common.model.ChurchDetail;

public class AdapterDao {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public AdapterDao() {
        // configures settings from hibernate.cfg.xml
        sessionFactory = new AnnotationConfiguration().configure()
                .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    public void save(Adapter adapter) {
        transaction = session.beginTransaction();
        session.save(adapter);
        transaction.commit();
    }

    public List<Adapter> getEnabledAdapters() {
        Query query = session.createQuery("from " + Adapter.class.getName()
                + " where enabled = :enabled");
        query.setParameter("enabled", Boolean.TRUE);
        List<Adapter> adapters = query.list();
        return adapters;
    }

    public void deleteAdapters() {
        transaction = session.beginTransaction();
        List<Adapter> adapters = getAdapters();
        for (int i = 0; i < adapters.size(); i++) {
            session.delete(adapters.get(i));
        }
        transaction.commit();
    }

    @Override
    protected void finalize() throws Throwable {
        session.flush();
        session.close();
        super.finalize();
    }

    public List<Adapter> getDisabledAdapters() {
        Query query = session.createQuery("from " + Adapter.class.getName()
                + " where enabled = :enabled");
        query.setParameter("enabled", Boolean.FALSE);
        List<Adapter> adapters = query.list();
        return adapters;
    }

    public void enableAdapter(Adapter adapter) {
        adapter.setEnabled(Boolean.TRUE);
        save(adapter);
    }

    public void disableAdapter(Adapter adapter) {
        adapter.setEnabled(Boolean.FALSE);
        save(adapter);
    }

    public List<Adapter> getAdapters() {
        Query query = session.createQuery("from " + Adapter.class.getName());
        List<Adapter> adapters = query.list();
        return adapters;
    }

    public Adapter get(Adapter adapter) {
        Query query = session.createQuery("from " + Adapter.class.getName()
                + " where id = :id");
        query.setParameter("id", adapter.getId());
        List<Adapter> adapters = query.list();
        Adapter savedAdapter = null;
        if (adapters.size() > 0) {
            savedAdapter = adapters.get(0);
        }
        return savedAdapter;
    }

    public Adapter findOneByPath(String txtAdapterPath) {
        Adapter savedAdapter = null;
        if (txtAdapterPath != null) {
            Query query = session.createQuery("from " + Adapter.class.getName()
                    + " where path = :path");
            query.setParameter("path", txtAdapterPath);
            List<Adapter> adapters = query.list();
            if (adapters.size() > 0) {
                savedAdapter = adapters.get(0);
            }
        }
        return savedAdapter;
    }

    public void deleteDataFor(Adapter adapter) {
        if (adapter != null && adapter.getId() != null) {
            ChurchDetail churchDetail = adapter.getChurchDetail();
            if (churchDetail != null) {
                transaction = session.beginTransaction();
                adapter.setChurchDetail(null);
                session.delete(churchDetail);
                session.save(adapter);
                transaction.commit();
            }
        }
    }
}
