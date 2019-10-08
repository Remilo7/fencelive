package com.fencelive.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "TOURNAMENT_FENCERS")
public class TournamentFencers {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament_id;

    @ManyToOne
    @JoinColumn(name = "fencer_id")
    private Fencer fencer_id;

    public TournamentFencers() {
    }

    public TournamentFencers(Tournament tournament_id, Fencer fencer_id) {
        this.tournament_id = tournament_id;
        this.fencer_id = fencer_id;
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
}
