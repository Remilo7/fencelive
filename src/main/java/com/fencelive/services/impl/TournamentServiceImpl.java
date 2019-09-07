package com.fencelive.services.impl;

import com.fencelive.dao.TournamentDAO;
import com.fencelive.model.entity.Tournament;
import com.fencelive.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    TournamentDAO tournamentDAO;

    @Override
    @Transactional
    public void add(Tournament tournament) {
        tournamentDAO.add(tournament);
    }

    @Override
    @Transactional
    public void edit(Tournament tournament) {
        tournamentDAO.edit(tournament);
    }

    @Override
    @Transactional
    public void delete(int id) {
        tournamentDAO.delete(id);
    }

    @Override
    @Transactional
    public Tournament getTournament(int id) {
        return tournamentDAO.getTournament(id);
    }

    @Override
    @Transactional
    public List getAllTournaments() {
        return tournamentDAO.getAllTournaments();
    }
}
