package com.example.SSK.model;

import javax.persistence.*;

@Entity
@Table(name = "infoUser", schema = "public")
public class InfoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name, userTag;
    private Long userID;

    public InfoUser() {
    }

    public InfoUser(Long userID,String name, String userTag) {
        this.name = name;
        this.userID = userID;
        this.userTag = userTag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserTag() {
        return userTag;
    }

    public void setUserTag(String userTag) {
        this.userTag = userTag;
    }
}
