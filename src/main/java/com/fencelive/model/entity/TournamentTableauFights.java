package com.fencelive.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "TOURNAMENT_TABLEAU_FIGHTS")
public class TournamentTableauFights {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament_id;

    @ManyToOne
    @JoinColumn(name = "fencer1_id")
    private Fencer fencer1_id;

    @ManyToOne
    @JoinColumn(name = "fencer2_id")
    private Fencer fencer2_id;

    @Column
    private int score1;

    @Column
    private int score2;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Fencer winner_id;

    public TournamentTableauFights() {
    }

    public TournamentTableauFights(Tournament tournament_id, Fencer fencer1_id, Fencer fencer2_id, int score1, int score2, Fencer winner_id) {
        this.tournament_id = tournament_id;
        this.fencer1_id = fencer1_id;
        this.fencer2_id = fencer2_id;
        this.score1 = score1;
        this.score2 = score2;
        this.winner_id = winner_id;
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

    public Fencer getFencer1_id() {
        return fencer1_id;
    }

    public void setFencer1_id(Fencer fencer1_id) {
        this.fencer1_id = fencer1_id;
    }

    public Fencer getFencer2_id() {
        return fencer2_id;
    }

    public void setFencer2_id(Fencer fencer2_id) {
        this.fencer2_id = fencer2_id;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public Fencer getWinner_id() {
        return winner_id;
    }

    public void setWinner_id(Fencer winner_id) {
        this.winner_id = winner_id;
    }
}
