package com.fencelive.services;

import com.fencelive.model.entity.Fencer;
import com.fencelive.model.entity.GroupFights;
import com.fencelive.model.entity.TournamentGroups;

import java.util.List;

public interface GroupFightsService {

    public void add(GroupFights groupFights);
    public void edit(GroupFights groupFights);
    public void delete(int id);
    public GroupFights getGroupFight(int id);
    public List getAllFencerGroupFights(Fencer fencer, List<TournamentGroups> tournamentGroups);
    public List getAllGroupFights(TournamentGroups tournamentGroups);
    public int getLastIndex();
}
