package com.fencelive.dao;

import com.fencelive.model.entity.GroupFencers;
import com.fencelive.model.entity.TournamentGroups;

import java.util.List;

public interface GroupFencersDAO {

    public void add(GroupFencers groupFencers);
    public void delete(int id);
    public GroupFencers getGroupFencers(int id);
    public List getAllGroupFencers(TournamentGroups tournamentGroup);
}
