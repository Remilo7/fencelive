package com.fencelive.services.impl;

import com.fencelive.dao.GroupFightsDAO;
import com.fencelive.model.entity.Fencer;
import com.fencelive.model.entity.GroupFights;
import com.fencelive.model.entity.TournamentGroups;
import com.fencelive.services.GroupFightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupFightsServiceImpl implements GroupFightsService {

    @Autowired
    GroupFightsDAO groupFightsDAO;

    @Override
    @Transactional
    public void add(GroupFights groupFights) {
        groupFightsDAO.add(groupFights);
    }

    @Override
    @Transactional
    public void edit(GroupFights groupFights) {
        groupFightsDAO.edit(groupFights);
    }

    @Override
    @Transactional
    public void delete(int id) {
        groupFightsDAO.delete(id);
    }

    @Override
    @Transactional
    public void deleteAll(TournamentGroups tournamentGroups) {
        groupFightsDAO.deleteAll(tournamentGroups);
    }

    @Override
    @Transactional
    public GroupFights getGroupFight(int id) {
        return groupFightsDAO.getGroupFight(id);
    }

    @Override
    @Transactional
    public List getAllFencerGroupFights(Fencer fencer, List<TournamentGroups> tournamentGroups) {
        return groupFightsDAO.getAllFencerGroupFights(fencer, tournamentGroups);
    }

    @Override
    @Transactional
    public List getAllGroupFights(TournamentGroups tournamentGroups) {
        return groupFightsDAO.getAllGroupFights(tournamentGroups);
    }

    @Override
    @Transactional
    public int getLastIndex() {
        return groupFightsDAO.getLastIndex();
    }
}
