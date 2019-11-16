package com.fencelive.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "TOURNAMENT_GROUP_CLASSLIST")
public class TournamentGroupClassList {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament_id;

    @ManyToOne
    @JoinColumn(name = "fencer_id")
    private Fencer fencer_id;

    @Column
    private double ind1;

    @Column
    private int ind2;

    @Column
    private int ind3;

    @Column
    private boolean classified;

    public TournamentGroupClassList() {
    }

    public TournamentGroupClassList(Tournament tournament_id, Fencer fencer_id, double ind1, int ind2, int ind3, boolean classified) {
        this.tournament_id = tournament_id;
        this.fencer_id = fencer_id;
        this.ind1 = ind1;
        this.ind2 = ind2;
        this.ind3 = ind3;
        this.classified = classified;
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

    public double getInd1() {
        return ind1;
    }

    public void setInd1(double ind1) {
        this.ind1 = ind1;
    }

    public int getInd2() {
        return ind2;
    }

    public void setInd2(int ind2) {
        this.ind2 = ind2;
    }

    public int getInd3() {
        return ind3;
    }

    public void setInd3(int ind3) {
        this.ind3 = ind3;
    }

    public boolean isClassified() {
        return classified;
    }

    public void setClassified(boolean classified) {
        this.classified = classified;
    }
}
