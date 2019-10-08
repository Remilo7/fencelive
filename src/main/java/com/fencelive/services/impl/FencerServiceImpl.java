package com.fencelive.services.impl;

import com.fencelive.dao.FencerDAO;
import com.fencelive.model.entity.Fencer;
import com.fencelive.services.FencerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FencerServiceImpl implements FencerService {

    @Autowired
    private FencerDAO fencerDAO;

    @Override
    @Transactional
    public void add(Fencer fencer) {
        fencerDAO.add(fencer);
    }

    @Override
    @Transactional
    public void edit(Fencer fencer) {
        fencerDAO.edit(fencer);
    }

    @Override
    @Transactional
    public void delete(int id) {
        fencerDAO.delete(id);
    }

    @Override
    @Transactional
    public Fencer getFencer(int id) {
        return fencerDAO.getFencer(id);
    }

    @Override
    @Transactional
    public List getEqualFencer(String name, String surname, int year) {
        return fencerDAO.getEqualFencer(name, surname, year);
    }

    @Override
    @Transactional
    public List getAllFencers() {
        return fencerDAO.getAllFencers();
    }

    @Override
    @Transactional
    public List getCategoryFencers(int from, int to) {
        return fencerDAO.getCategoryFencers(from, to);
    }
}
