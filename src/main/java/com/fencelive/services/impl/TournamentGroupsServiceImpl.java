package com.fencelive.services.impl;

import com.fencelive.dao.TournamentGroupsDAO;
import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentGroups;
import com.fencelive.services.TournamentGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TournamentGroupsServiceImpl implements TournamentGroupsService {

    @Autowired
    TournamentGroupsDAO tournamentGroupsDAO;

    @Override
    @Transactional
    public void add(Tournament tournament, int group_id, int group_card) {
        tournamentGroupsDAO.add(tournament, group_id, group_card);
    }

    @Override
    @Transactional
    public void delete(int id) {
        tournamentGroupsDAO.delete(id);
    }

    @Override
    @Transactional
    public TournamentGroups getTournamentGroup(int id) {
        return tournamentGroupsDAO.getTournamentGroup(id);
    }

    @Override
    @Transactional
    public List getTournamentGroups(Tournament tournament) {
        return tournamentGroupsDAO.getTournamentGroups(tournament);
    }

    @Override
    @Transactional
    public int getLastIndex() {
        return tournamentGroupsDAO.getLastIndex();
    }
}
