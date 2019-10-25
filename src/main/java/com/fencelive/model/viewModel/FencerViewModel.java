package com.fencelive.model.viewModel;

import com.fencelive.model.entity.Fencer;

import java.util.List;

public class FencerViewModel {

    private int id;
    private String name;
    private String club;
    private String country;
    private int year;
    private List<Integer> idList;
    private boolean competing;

    public FencerViewModel() {
    }

    public FencerViewModel(int id, String name, String club, String country, int year) {
        this.id = id;
        this.name = name;
        this.club = club;
        this.country = country;
        this.year = year;
        competing = false;
    }

    public FencerViewModel(Fencer fencer) {
        id = fencer.getId();
        name = fencer.getSurname()+" "+fencer.getName();
        club = fencer.getClub();
        country = fencer.getCountry();
        year = fencer.getYear();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public boolean getCompeting() {
        return competing;
    }

    public void setCompeting(boolean competing) {
        this.competing = competing;
    }
}
