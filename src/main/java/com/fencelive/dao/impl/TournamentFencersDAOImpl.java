package com.fencelive.dao.impl;

import com.fencelive.dao.TournamentFencersDAO;
import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentFencers;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TournamentFencersDAOImpl implements TournamentFencersDAO {

    @Autowired
    private SessionFactory session;

    @Override
    public void add(TournamentFencers tournament_fencers) {
        session.getCurrentSession().save(tournament_fencers);
    }

    @Override
    public void delete(int id) {
        session.getCurrentSession().delete(getTournamentFencers(id));
    }

    @Override
    public void deleteAll(Tournament tournament) {

        String hql = "SELECT tf.id FROM TournamentFencers tf WHERE tf.tournament_id.id = :tid";

        List result = session.getCurrentSession().createQuery(hql)
                .setParameter("tid", tournament.getId())
                .list();

        for (int i=0; i<result.size(); i++)
            delete((int)result.get(i));
    }

    @Override
    public TournamentFencers getTournamentFencers(int id) {
        return session.getCurrentSession().get(TournamentFencers.class, id);
    }

    @Override
    public List getAllTournamentFencers(Tournament tournament) {

        String hql = "SELECT tf.fencer_id FROM TournamentFencers tf WHERE tf.tournament_id.id = :tid";

        List result = session.getCurrentSession().createQuery(hql)
                .setParameter("tid", tournament.getId())
                .list();

        return result;
    }

    @Override
    public List getAllFencers(Tournament tournament) {

        String hql = "FROM TournamentFencers tf WHERE tf.tournament_id.id = :tid";

        List result = session.getCurrentSession().createQuery(hql)
                .setParameter("tid", tournament.getId())
                .list();

        return result;
    }
}
