package com.fencelive.dao.impl;

import com.fencelive.dao.TournamentDAO;
import com.fencelive.model.entity.Tournament;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TournamentDAOImpl implements TournamentDAO {

    @Autowired
    private SessionFactory session;

    @Override
    public void add(Tournament tournament) {
        session.getCurrentSession().save(tournament);
    }

    @Override
    public void edit(Tournament tournament) {
        session.getCurrentSession().update(tournament);
    }

    @Override
    public void delete(int id) {
        session.getCurrentSession().delete(getTournament(id));
    }

    @Override
    public Tournament getTournament(int id) {
        return session.getCurrentSession().get(Tournament.class, id);
    }

    @Override
    public List getAllTournaments() {
        return session.getCurrentSession().createQuery("FROM Tournament ORDER BY date DESC").list();
    }
}
