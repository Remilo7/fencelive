package com.fencelive.dao.impl;

import com.fencelive.dao.ListDAO;
import com.fencelive.model.entity.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ListDAOImpl implements ListDAO {

    @Autowired
    private SessionFactory session;

    @Override
    public void add(List list) {
        session.getCurrentSession().save(list);
    }

    @Override
    public void edit(List list) {
        session.getCurrentSession().update(list);
    }

    @Override
    public void delete(int id) {
        session.getCurrentSession().delete(getList(id));
    }

    @Override
    public List getList(int id) {
        return session.getCurrentSession().get(List.class, id);
    }

    @Override
    public java.util.List getAllLists() {
        return session.getCurrentSession().createQuery("FROM List ORDER BY id DESC").list();
    }
}
