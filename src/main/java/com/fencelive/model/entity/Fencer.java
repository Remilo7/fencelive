package com.fencelive.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FENCERS")
public class Fencer {

    @Id
    private int id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private int year;

    @Column
    private char sex;

    @Column
    private String club;

    @Column
    private String country;

    public Fencer() {
    }

    public Fencer(String name, String surname, int year, char sex, String club, String country) {
        this.name = name;
        this.surname = surname;
        this.year = year;
        this.sex = sex;
        this.club = club;
        this.country = country;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Fencer)) {
            return false;
        }

        Fencer f = (Fencer)obj;

        return ((name.equalsIgnoreCase(f.name)) && (surname.equalsIgnoreCase(f.surname)) && (club.equalsIgnoreCase(f.club)));
    }
}
