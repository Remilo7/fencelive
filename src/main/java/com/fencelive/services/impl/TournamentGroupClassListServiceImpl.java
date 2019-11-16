package com.fencelive.services.impl;

import com.fencelive.dao.TournamentGroupClassListDAO;
import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentGroupClassList;
import com.fencelive.services.TournamentGroupClassListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TournamentGroupClassListServiceImpl implements TournamentGroupClassListService {

    @Autowired
    TournamentGroupClassListDAO tournamentGroupClassListDAO;

    @Override
    @Transactional
    public void add(TournamentGroupClassList tournamentGroupClassList) {
        tournamentGroupClassListDAO.add(tournamentGroupClassList);
    }

    @Override
    @Transactional
    public void delete(int id) {
        tournamentGroupClassListDAO.delete(id);
    }

    @Override
    @Transactional
    public TournamentGroupClassList getTournamentGroupClassList(int id) {
        return tournamentGroupClassListDAO.getTournamentGroupClassList(id);
    }

    @Override
    @Transactional
    public List getClassList(Tournament tournament) {
        return tournamentGroupClassListDAO.getClassList(tournament);
    }
}
