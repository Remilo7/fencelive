package com.fencelive.services.impl;

import com.fencelive.dao.TournamentFinalClassListDAO;
import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentFinalClassList;
import com.fencelive.services.TournamentFinalClassListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TournamentFinalClassListServiceImpl implements TournamentFinalClassListService {

    @Autowired
    private TournamentFinalClassListDAO tournamentFinalClassListDAO;

    @Override
    @Transactional
    public void add(TournamentFinalClassList tournamentGroupClassList) {
        tournamentFinalClassListDAO.add(tournamentGroupClassList);
    }

    @Override
    @Transactional
    public void edit(TournamentFinalClassList tournamentFinalClassList) {
        tournamentFinalClassListDAO.edit(tournamentFinalClassList);
    }

    @Override
    @Transactional
    public void delete(int id) {
        tournamentFinalClassListDAO.delete(id);
    }

    @Override
    @Transactional
    public void clear() {
        tournamentFinalClassListDAO.clear();
    }

    @Override
    @Transactional
    public TournamentFinalClassList getTournamentGroupClassList(int id) {
        return tournamentFinalClassListDAO.getTournamentGroupClassList(id);
    }

    @Override
    @Transactional
    public List getFinalClassList(Tournament tournament) {
        return tournamentFinalClassListDAO.getFinalClassList(tournament);
    }

    @Override
    @Transactional
    public List getTopClassList(Tournament tournament, int lim) {
        return tournamentFinalClassListDAO.getTopClassList(tournament, lim);
    }
}
