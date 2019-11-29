package com.fencelive.services.impl;

import com.fencelive.dao.TournamentTableauFightsDAO;
import com.fencelive.model.entity.Fencer;
import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentTableauFights;
import com.fencelive.services.TournamentTableauFightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TournamentTableauFightsServiceImpl implements TournamentTableauFightsService {

    @Autowired
    TournamentTableauFightsDAO tournamentTableauFightsDAO;

    @Override
    @Transactional
    public void add(TournamentTableauFights fight) {
        tournamentTableauFightsDAO.add(fight);
    }

    @Override
    @Transactional
    public void edit(TournamentTableauFights fight) {
        tournamentTableauFightsDAO.edit(fight);
    }

    @Override
    @Transactional
    public void delete(int id) {
        tournamentTableauFightsDAO.delete(id);
    }

    @Override
    @Transactional
    public TournamentTableauFights getTournamentTableFight(int id) {
        return tournamentTableauFightsDAO.getTournamentTableFight(id);
    }

    @Override
    @Transactional
    public List getAllTournamentTableFights(Tournament tournament, int table) {
        return tournamentTableauFightsDAO.getAllTournamentTableFights(tournament, table);
    }

    @Override
    @Transactional
    public int getMinTable(Tournament tournament) {
        return tournamentTableauFightsDAO.getMinTable(tournament);
    }

    @Override
    @Transactional
    public int getFencerMinTable(Tournament tournament, Fencer fencer) {
        return tournamentTableauFightsDAO.getFencerMinTable(tournament, fencer);
    }
}
