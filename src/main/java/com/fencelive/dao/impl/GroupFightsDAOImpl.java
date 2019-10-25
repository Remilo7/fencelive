package com.fencelive.dao.impl;

import com.fencelive.dao.GroupFightsDAO;
import com.fencelive.model.entity.Fencer;
import com.fencelive.model.entity.GroupFights;
import com.fencelive.model.entity.TournamentGroups;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupFightsDAOImpl implements GroupFightsDAO {

    @Autowired
    private SessionFactory session;

    @Override
    public void add(GroupFights groupFights) {
        session.getCurrentSession().save(groupFights);
    }

    @Override
    public void edit(GroupFights groupFights) {
        session.getCurrentSession().update(groupFights);
    }

    @Override
    public void delete(int id) {
        session.getCurrentSession().delete(getGroupFight(id));
    }

    @Override
    public GroupFights getGroupFight(int id) {
        return session.getCurrentSession().get(GroupFights.class, id);
    }

    @Override
    public List getAllFencerGroupFights(Fencer fencer) {

        String hql = "FROM GroupFights gf WHERE gf.fencer1_id = :fid OR gf.fencer2_id = :fid";

        List result = session.getCurrentSession().createQuery(hql)
                .setParameter("fid", fencer)
                .list();

        return result;
    }

    @Override
    public List getAllGroupFights(TournamentGroups tournamentGroups) {

        String hql = "FROM GroupFights gf WHERE gf.group_id = :gid";

        List result = session.getCurrentSession().createQuery(hql)
                .setParameter("gid", tournamentGroups)
                .list();

        return result;
    }

    @Override
    public int getLastIndex() {

        String hql = "SELECT MAX(gf.id) FROM GroupFights gf";

        List result = session.getCurrentSession().createQuery(hql).list();

        return (int)result.get(0);
    }
}
