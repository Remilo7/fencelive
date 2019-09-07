package com.fencelive.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TOURNAMENTS")
public class Tournament {

    @Id
    private int id;

    @Column
    private String name;

    @Column
    private String date;

    @Column
    private int category;

    @Column
    private char sex;

    @Column
    private int outamount;

    @Column
    private int method;

    @Column
    private int list;

    public Tournament() {
    }

    public Tournament(String name, String date, int category, char sex, int outamount, int method, int list) {
        this.name = name;
        this.date = date;
        this.category = category;
        this.sex = sex;
        this.outamount = outamount;
        this.method = method;
        this.list = list;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getOutamount() {
        return outamount;
    }

    public void setOutamount(int out) {
        this.outamount = out;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public int getList() {
        return list;
    }

    public void setList(int list) {
        this.list = list;
    }
}

