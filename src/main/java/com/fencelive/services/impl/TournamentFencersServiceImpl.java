package com.fencelive.services.impl;

import com.fencelive.dao.TournamentFencersDAO;
import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentFencers;
import com.fencelive.services.TournamentFencersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TournamentFencersServiceImpl implements TournamentFencersService {

    @Autowired
    TournamentFencersDAO tournamentFencersDAO;

    @Override
    @Transactional
    public void add(TournamentFencers tournament_fencers) {
        tournamentFencersDAO.add(tournament_fencers);
    }

    @Override
    @Transactional
    public void delete(int id) {
        tournamentFencersDAO.delete(id);
    }

    @Override
    @Transactional
    public void deleteAll(Tournament tournament) {
        tournamentFencersDAO.deleteAll(tournament);
    }

    @Override
    @Transactional
    public TournamentFencers getTournamentFencers(int id) {
        return tournamentFencersDAO.getTournamentFencers(id);
    }

    @Override
    @Transactional
    public List getAllTournamentFencers(Tournament tournament_id) {
        return tournamentFencersDAO.getAllTournamentFencers(tournament_id);
    }

    @Override
    @Transactional
    public List getAllFencers(Tournament tournament_id) {
        return tournamentFencersDAO.getAllFencers(tournament_id);
    }
}
