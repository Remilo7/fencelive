package com.fencelive.services;

import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentTableauFights;

import java.util.List;

public interface TournamentTableauFightsService {

    public void add(TournamentTableauFights fight);
    public void edit(TournamentTableauFights fight);
    public void delete(int id);
    public TournamentTableauFights getTournamentTableFight(int id);
    public List getAllTournamentTableFights(Tournament tournament, int table);
    public int getMinTable(Tournament tournament);
}
