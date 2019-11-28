package com.fencelive.dao.impl;

import com.fencelive.dao.TournamentTableauFightsDAO;
import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentTableauFights;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TournamentTableauFightsDAOImpl implements TournamentTableauFightsDAO {

    @Autowired
    private SessionFactory session;

    @Override
    public void add(TournamentTableauFights fight) {
        session.getCurrentSession().save(fight);
    }

    @Override
    public void edit(TournamentTableauFights fight) {
        session.getCurrentSession().update(fight);
    }

    @Override
    public void delete(int id) {
        session.getCurrentSession().delete(getTournamentTableFight(id));
    }

    @Override
    public TournamentTableauFights getTournamentTableFight(int id) {
        return session.getCurrentSession().get(TournamentTableauFights.class, id);
    }

    @Override
    public List getAllTournamentTableFights(Tournament tournament, int table) {

        String hql = "FROM TournamentTableauFights ttf WHERE ttf.tournament_id.id = :tid AND ttf.tableau = :tnum";

        java.util.List result = session.getCurrentSession().createQuery(hql)
                .setParameter("tid", tournament.getId())
                .setParameter("tnum", table)
                .list();

        return result;
    }

    @Override
    public int getMinTable(Tournament tournament) {

        String hql = "SELECT MIN(tableau) FROM TournamentTableauFights ttf WHERE ttf.tournament_id.id = :tid";

        java.util.List result = session.getCurrentSession().createQuery(hql)
                .setParameter("tid", tournament.getId())
                .list();

        return (int)result.get(0);
    }
}
