package com.fencelive.services.impl;

import com.fencelive.dao.GroupFencersDAO;
import com.fencelive.model.entity.GroupFencers;
import com.fencelive.model.entity.TournamentGroups;
import com.fencelive.services.GroupFencersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupFencersServiceImpl implements GroupFencersService {

    @Autowired
    GroupFencersDAO groupFencersDAO;

    @Override
    @Transactional
    public void add(GroupFencers groupFencers) {
        groupFencersDAO.add(groupFencers);
    }

    @Override
    @Transactional
    public void delete(int id) {
        groupFencersDAO.delete(id);
    }

    @Override
    @Transactional
    public void deleteAll(TournamentGroups tournamentGroups) {
        groupFencersDAO.deleteAll(tournamentGroups);
    }

    @Override
    @Transactional
    public GroupFencers getGroupFencers(int id) {
        return groupFencersDAO.getGroupFencers(id);
    }

    @Override
    @Transactional
    public List getAllGroupFencers(TournamentGroups tournamentGroup) {
        return groupFencersDAO.getAllGroupFencers(tournamentGroup);
    }
}
