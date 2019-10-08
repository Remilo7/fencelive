package com.fencelive.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORIES")
public class Category {

    @Id
    private int id;

    @Column
    private String shortcut;

    @Column
    private String name;

    @Column
    private int min_age;

    @Column
    private int max_age;

    public Category() {
    }

    public Category(int id, String shortcut, String name, int min_age, int max_age) {
        this.id = id;
        this.shortcut = shortcut;
        this.name = name;
        this.min_age = min_age;
        this.max_age = max_age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMin_age() {
        return min_age;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }

    public int getMax_age() {
        return max_age;
    }

    public void setMax_age(int max_age) {
        this.max_age = max_age;
    }
}
