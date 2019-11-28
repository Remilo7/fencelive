package com.fencelive.dao;

import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentFinalClassList;

import java.util.List;

public interface TournamentFinalClassListDAO {

    public void add(TournamentFinalClassList tournamentGroupClassList);
    public void edit(TournamentFinalClassList tournamentFinalClassList);
    public void delete(int id);
    public TournamentFinalClassList getTournamentGroupClassList(int id);
    public List getFinalClassList(Tournament tournament);
    public List getTopClassList(Tournament tournament, int lim);
}
