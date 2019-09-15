package com.fencelive.dao;

import com.fencelive.model.entity.Fencer;

import java.util.List;

public interface FencerDAO {

    public void add(Fencer fencer);
    public void edit(Fencer fencer);
    public void delete(int id);
    public Fencer getFencer(int id);
    public List getEqualFencer(String name, String surname, int year);
    public List getAllFencers();
}
