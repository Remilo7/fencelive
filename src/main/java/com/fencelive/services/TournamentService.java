package com.fencelive.services;

import com.fencelive.model.entity.Tournament;

import java.util.List;

public interface TournamentService {

    public void add(Tournament tournament);
    public void edit(Tournament tournament);
    public void delete(int id);
    public Tournament getTournament(int id);
    public List getAllTournaments();
}
