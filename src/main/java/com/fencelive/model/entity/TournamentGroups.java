package com.fencelive.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "TOURNAMENT_GROUPS")
public class TournamentGroups {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament_id;

    @Column
    private int group_id;

    public TournamentGroups() {
    }

    public TournamentGroups(Tournament tournament_id, int group_id) {
        this.tournament_id = tournament_id;
        this.group_id = group_id;
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

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }
}
