package com.fencelive.services;

import com.fencelive.model.entity.GroupFencers;
import com.fencelive.model.entity.TournamentGroups;

import java.util.List;

public interface GroupFencersService {

    public void add(GroupFencers groupFencers);
    public void delete(int id);
    public void deleteAll(TournamentGroups tournamentGroups);
    public GroupFencers getGroupFencers(int id);
    public List getAllGroupFencers(TournamentGroups tournamentGroup);
}
