package com.fencelive.model.entity;


import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ROLES")
public class UserRole {

    @Id
    private int role_id;

    @Column
    private String username;

    @Column
    private String user_role;

    public UserRole() {
    }

    public UserRole(String username, String user_role) {
        this.username = username;
        this.user_role = user_role;
    }

    public int getRole_Id() {
        return role_id;
    }

    public void setRole_Id(int role_id) {
        this.role_id = role_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}
