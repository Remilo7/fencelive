package com.fencelive.dao;

import com.fencelive.model.entity.Fencer;
import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentTableauFights;

import java.util.List;

public interface TournamentTableauFightsDAO {

    public void add(TournamentTableauFights fight);
    public void edit(TournamentTableauFights fight);
    public void delete(int id);
    public TournamentTableauFights getTournamentTableFight(int id);
    public List getAllTournamentTableFights(Tournament tournament, int table);
    public int getMinTable(Tournament tournament);
    public int getFencerMinTable(Tournament tournament, Fencer fencer);
}
