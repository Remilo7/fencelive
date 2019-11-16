package com.fencelive.dao.impl;

import com.fencelive.dao.TournamentGroupClassListDAO;
import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentGroupClassList;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TournamentGroupClassListDAOImpl implements TournamentGroupClassListDAO {

    @Autowired
    private SessionFactory session;

    @Override
    public void add(TournamentGroupClassList tournamentGroupClassList) {
        session.getCurrentSession().save(tournamentGroupClassList);
    }

    @Override
    public void delete(int id) {
        session.getCurrentSession().delete(getTournamentGroupClassList(id));
    }

    @Override
    public TournamentGroupClassList getTournamentGroupClassList(int id) {
        return session.getCurrentSession().get(TournamentGroupClassList.class, id);
    }

    @Override
    public List getClassList(Tournament tournament) {

        String hql = "FROM TournamentGroupClassList tcl WHERE tcl.tournament_id.id = :tid";

        java.util.List result = session.getCurrentSession().createQuery(hql)
                .setParameter("tid", tournament.getId())
                .list();

        return result;
    }
}
