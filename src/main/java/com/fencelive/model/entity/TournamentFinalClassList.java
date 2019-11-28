package com.fencelive.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "TOURNAMENT_FINAL_CLASSLIST")
public class TournamentFinalClassList {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament_id;

    @ManyToOne
    @JoinColumn(name = "fencer_id")
    private Fencer fencer_id;

    @Column
    private int place;

    public TournamentFinalClassList() {
    }

    public TournamentFinalClassList(Tournament tournament_id, Fencer fencer_id, int place) {
        this.tournament_id = tournament_id;
        this.fencer_id = fencer_id;
        this.place = place;
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

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
