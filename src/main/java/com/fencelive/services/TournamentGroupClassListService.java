package com.fencelive.services;

import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentGroupClassList;

import java.util.List;

public interface TournamentGroupClassListService {

    public void add(TournamentGroupClassList tournamentGroupClassList);
    public void delete(int id);
    public TournamentGroupClassList getTournamentGroupClassList(int id);
    public List getClassList(Tournament tournament);
}
