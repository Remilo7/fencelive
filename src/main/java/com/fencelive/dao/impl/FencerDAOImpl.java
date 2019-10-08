package com.fencelive.dao.impl;

import com.fencelive.dao.FencerDAO;
import com.fencelive.model.entity.Fencer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FencerDAOImpl implements FencerDAO {

    @Autowired
    SessionFactory session;

    @Override
    public void add(Fencer fencer) {
        session.getCurrentSession().save(fencer);
    }

    @Override
    public void edit(Fencer fencer) {
        session.getCurrentSession().update(fencer);
    }

    @Override
    public void delete(int id) {
        session.getCurrentSession().delete(getFencer(id));
    }

    @Override
    public Fencer getFencer(int id) {
        return session.getCurrentSession().get(Fencer.class, id);
    }

    @Override
    public List getEqualFencer(String name, String surname, int year) {
        String sql = "FROM Fencer WHERE name='"+name+"' AND surname='"+surname+"' AND year='"+year+"'";
        return session.getCurrentSession().createQuery(sql).list();
    }

    @Override
    public List getAllFencers() {

        return session.getCurrentSession().createQuery("FROM Fencer ORDER BY club, surname, name").list();
    }

    @Override
    public List getCategoryFencers(int from, int to) {

        String hql = "FROM Fencer f WHERE f.year BETWEEN :fr AND :t ORDER BY club, surname, name";

        List result = session.getCurrentSession().createQuery(hql)
                .setParameter("fr", from)
                .setParameter("t", to)
                .list();

        return result;
    }
}
