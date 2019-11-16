package com.fencelive.model.viewModel;

import com.fencelive.model.entity.Fencer;

public class ClassFencerViewModel {

    private int id;
    private String name;
    private String club;
    private String country;
    private double ind1;
    private int ind2;
    private int ind3;
    private boolean classified;

    public ClassFencerViewModel() {
    }

    public ClassFencerViewModel(int id, String name, String club, String country) {
        this.id = id;
        this.name = name;
        this.club = club;
        this.country = country;
    }

    public ClassFencerViewModel(Fencer fencer) {
        id = fencer.getId();
        name = fencer.getSurname()+" "+fencer.getName();
        club = fencer.getClub();
        country = fencer.getCountry();
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
