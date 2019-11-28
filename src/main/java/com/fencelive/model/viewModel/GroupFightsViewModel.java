package com.fencelive.model.viewModel;

import com.fencelive.model.entity.Fencer;
import com.fencelive.model.entity.GroupFights;
import com.fencelive.model.entity.TournamentGroups;
import com.fencelive.model.entity.TournamentTableauFights;

import java.util.HashMap;
import java.util.Map;

public class GroupFightsViewModel {

    private int id;
    private TournamentGroups group_id;
    private int tableau;
    private Fencer fencer1_id;
    private Fencer fencer2_id;
    private String score1;
    private String score2;

    public GroupFightsViewModel() {
    }

    public GroupFightsViewModel(GroupFights groupFights) {
        id = groupFights.getId();
        group_id = groupFights.getGroup_id();
        fencer1_id = groupFights.getFencer1_id();
        fencer2_id = groupFights.getFencer2_id();

        if (groupFights.getWinner_id() != null){

            Map<String,String> map = getScores(groupFights);
            score1 = map.get("score1");
            score2 = map.get("score2");
        }
    }

    public GroupFightsViewModel(TournamentTableauFights tableauFights){
        id = tableauFights.getId();
        tableau = tableauFights.getTableau();
        fencer1_id = tableauFights.getFencer1_id();
        fencer2_id = tableauFights.getFencer2_id();

        if (tableauFights.getWinner_id() != null){

            Map<String,String> map = getScores(tableauFights);
            score1 = map.get("score1");
            score2 = map.get("score2");
        }
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

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public int getTableau() {
        return tableau;
    }

    public void setTableau(int tableau) {
        this.tableau = tableau;
    }

    private Map<String, String> getScores(GroupFights groupFights) {

        String score1 = "";
        String score2 = "";

        if (groupFights.getFencer1_id().equals(groupFights.getWinner_id())){
            score1 = "V"+groupFights.getScore1();
            score2 = "D"+groupFights.getScore2();
        }

        else if (groupFights.getFencer2_id().equals(groupFights.getWinner_id())){
            score1 = "D"+groupFights.getScore1();
            score2 = "V"+groupFights.getScore2();
        }

        Map<String, String> map = new HashMap<>();
        map.put("score1",score1);
        map.put("score2",score2);

        return map;
    }

    private Map<String, String> getScores(TournamentTableauFights tableauFights) {

        String score1 = "";
        String score2 = "";

        if (tableauFights.getFencer1_id().equals(tableauFights.getWinner_id())){
            score1 = "V"+tableauFights.getScore1();
            score2 = "D"+tableauFights.getScore2();
        }

        else if (tableauFights.getFencer2_id().equals(tableauFights.getWinner_id())){
            score1 = "D"+tableauFights.getScore1();
            score2 = "V"+tableauFights.getScore2();
        }

        Map<String, String> map = new HashMap<>();
        map.put("score1",score1);
        map.put("score2",score2);

        return map;
    }
}
