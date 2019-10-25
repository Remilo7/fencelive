package com.fencelive.dao.impl;

import com.fencelive.dao.TournamentGroupsDAO;
import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentGroups;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TournamentGroupsDAOImpl implements TournamentGroupsDAO {

    @Autowired
    private SessionFactory session;

    @Override
    public void add(Tournament tournament, int group_id, int group_card) {
        session.getCurrentSession().save(new TournamentGroups(tournament, group_id, group_card));
    }

    @Override
    public void delete(int id) {
        session.getCurrentSession().delete(getTournamentGroup(id));
    }

    @Override
    public TournamentGroups getTournamentGroup(int id) {
        return session.getCurrentSession().get(TournamentGroups.class, id);
    }

    @Override
    public List getTournamentGroups(Tournament tournament) {

        String hql = "FROM TournamentGroups tg WHERE tg.tournament_id.id = :tid";

        List result = session.getCurrentSession().createQuery(hql)
                .setParameter("tid", tournament.getId())
                .list();

        return result;
    }

    @Override
    public int getLastIndex() {

        String hql = "SELECT MAX(tg.id) FROM TournamentGroups tg";

        List result = session.getCurrentSession().createQuery(hql).list();

        return (int)result.get(0);
    }
}
