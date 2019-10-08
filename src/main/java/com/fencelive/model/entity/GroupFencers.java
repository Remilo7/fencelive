package com.fencelive.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "GROUP_FENCERS")
public class GroupFencers {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private TournamentGroups group_id;

    @ManyToOne
    @JoinColumn(name = "fencer_id")
    private Fencer fencer_id;

    public GroupFencers() {
    }

    public GroupFencers(TournamentGroups group_id, Fencer fencer_id) {
        this.group_id = group_id;
        this.fencer_id = fencer_id;
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

    public Fencer getFencer_id() {
        return fencer_id;
    }

    public void setFencer_id(Fencer fencer_id) {
        this.fencer_id = fencer_id;
    }
}
