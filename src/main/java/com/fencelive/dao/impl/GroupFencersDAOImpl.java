package com.fencelive.dao.impl;

import com.fencelive.dao.GroupFencersDAO;
import com.fencelive.model.entity.GroupFencers;
import com.fencelive.model.entity.TournamentGroups;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupFencersDAOImpl implements GroupFencersDAO {

    @Autowired
    private SessionFactory session;

    @Override
    public void add(GroupFencers groupFencers) {
        session.getCurrentSession().save(groupFencers);
    }

    @Override
    public void delete(int id) {
        session.getCurrentSession().delete(getGroupFencers(id));
    }

    @Override
    public GroupFencers getGroupFencers(int id) {
        return session.getCurrentSession().get(GroupFencers.class, id);
    }

    @Override
    public List getAllGroupFencers(TournamentGroups tournamentGroup) {

        String hql = "SELECT gf.fencer_id.id FROM GroupFencers gf WHERE gf.group_id = :gid";

        List result = session.getCurrentSession().createQuery(hql)
                .setParameter("gid", tournamentGroup)
                .list();

        return result;
    }
}
