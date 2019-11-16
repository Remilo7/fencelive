package com.fencelive.dao;

import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentGroupClassList;

import java.util.List;

public interface TournamentGroupClassListDAO {

    public void add(TournamentGroupClassList tournamentGroupClassList);
    public void delete(int id);
    public TournamentGroupClassList getTournamentGroupClassList(int id);
    public List getClassList(Tournament tournament);
}
