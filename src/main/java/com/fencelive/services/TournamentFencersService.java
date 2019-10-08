package com.fencelive.services;

import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentFencers;

import java.util.List;

public interface TournamentFencersService {

    public void add(TournamentFencers tournament_fencers);
    public void delete(int id);
    public TournamentFencers getTournamentFencers(int id);
    public List getAllTournamentFencers(Tournament tournament_id);
    public List getAllFencers(Tournament tournament_id);
}
