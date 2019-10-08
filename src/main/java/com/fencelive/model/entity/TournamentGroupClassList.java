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
    private int win_amount;

    @Column
    private int score;

    public TournamentGroupClassList() {
    }

    public TournamentGroupClassList(Tournament tournament_id, Fencer fencer_id, int win_amount, int score) {
        this.tournament_id = tournament_id;
        this.fencer_id = fencer_id;
        this.win_amount = win_amount;
        this.score = score;
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

    public int getWin_amount() {
        return win_amount;
    }

    public void setWin_amount(int win_amount) {
        this.win_amount = win_amount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
