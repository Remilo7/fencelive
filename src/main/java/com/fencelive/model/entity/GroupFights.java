package com.fencelive.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "GROUP_FIGHTS")
public class GroupFights {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private TournamentGroups group_id;

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

    public GroupFights() {
    }

    public GroupFights(TournamentGroups group_id, Fencer fencer1_id, Fencer fencer2_id, int score1, int score2, Fencer winner_id) {
        this.group_id = group_id;
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

    public TournamentGroups getGroup_id() {
        return group_id;
    }

    public void setGroup_id(TournamentGroups group_id) {
        this.group_id = group_id;
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
