package com.fencelive.services;

import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentFinalClassList;

import java.util.List;

public interface TournamentFinalClassListService {

    public void add(TournamentFinalClassList tournamentGroupClassList);
    public void edit(TournamentFinalClassList tournamentFinalClassList);
    public void delete(int id);
    public void clear();
    public TournamentFinalClassList getTournamentGroupClassList(int id);
    public List getFinalClassList(Tournament tournament);
    public List getTopClassList(Tournament tournament, int lim);
}
