package com.fencelive.services;

import com.fencelive.model.entity.Fencer;

import java.util.List;

public interface FencerService {

    public void add(Fencer fencer);
    public void edit(Fencer fencer);
    public void delete(int id);
    public Fencer getFencer(int id);
    public List getAllFencers();
}
