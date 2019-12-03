package com.fencelive.dao.impl;

import com.fencelive.dao.TournamentFinalClassListDAO;
import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentFinalClassList;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TournamentFinalClassListDAOImpl implements TournamentFinalClassListDAO {

    @Autowired
    private SessionFactory session;

    @Override
    public void add(TournamentFinalClassList tournamentGroupClassList) {
        session.getCurrentSession().save(tournamentGroupClassList);
    }

    @Override
    public void edit(TournamentFinalClassList tournamentFinalClassList) {
        session.getCurrentSession().update(tournamentFinalClassList);
    }

    @Override
    public void delete(int id) {
        session.getCurrentSession().delete(getTournamentGroupClassList(id));
    }

    @Override
    public void delete(Tournament tournament) {

        String hql = "SELECT tfcl.id FROM TournamentFinalClassList tfcl WHERE tfcl.tournament_id.id = :tid";

        List result = session.getCurrentSession().createQuery(hql)
                .setParameter("tid", tournament.getId())
                .list();

        for(int i=0; i<result.size(); i++)
            delete((int)result.get(i));
    }

    @Override
    public void clear() {
        session.getCurrentSession().createQuery("DELETE FROM TournamentFinalClassList ").executeUpdate();
    }

    @Override
    public TournamentFinalClassList getTournamentGroupClassList(int id) {
        return session.getCurrentSession().get(TournamentFinalClassList.class, id);
    }

    @Override
    public List getFinalClassList(Tournament tournament) {

        String hql = "FROM TournamentFinalClassList tfcl WHERE tfcl.tournament_id.id = :tid ORDER BY tfcl.place";

        List result = session.getCurrentSession().createQuery(hql)
                .setParameter("tid", tournament.getId())
                .list();

        return result;
    }

    @Override
    public List getTopClassList(Tournament tournament, int lim) {

        String hql = "FROM TournamentFinalClassList tfcl WHERE tfcl.tournament_id.id = :tid ORDER BY tfcl.place";

        List result = session.getCurrentSession().createQuery(hql)
                .setParameter("tid", tournament.getId())
                .setMaxResults(lim)
                .list();

        return result;
    }
}
