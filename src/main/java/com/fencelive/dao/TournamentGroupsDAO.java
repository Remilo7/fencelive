package com.fencelive.dao;

import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentGroups;

import java.util.List;

public interface TournamentGroupsDAO {

    public void add(Tournament tournament, int group_id, int group_card);
    public void delete(int id);
    public TournamentGroups getTournamentGroup(int id);
    public List getTournamentGroups(Tournament tournament);
    public int getLastIndex();
}
