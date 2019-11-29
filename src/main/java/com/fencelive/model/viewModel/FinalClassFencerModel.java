package com.fencelive.model.viewModel;

import com.fencelive.model.entity.Fencer;
import com.fencelive.model.entity.Tournament;

public class FinalClassFencerModel {

    private int id;
    private Tournament tournament_id;
    private Fencer fencer_id;
    private int tableau;
    private int place;

    public FinalClassFencerModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tournament getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(Tournament tournament_id) {
        this.tournament_id = tournament_id;
    }

    public Fencer getFencer_id() {
        return fencer_id;
    }

    public void setFencer_id(Fencer fencer_id) {
        this.fencer_id = fencer_id;
    }

    public int getTableau() {
        return tableau;
    }

    public void setTableau(int tableau) {
        this.tableau = tableau;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
