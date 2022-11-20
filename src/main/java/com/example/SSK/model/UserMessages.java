package com.example.SSK.model;

import javax.persistence.*;

@Entity
@Table(name = "infoMessage", schema = "public")
public class UserMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name, userTag, message;
    private Long userID;

    public UserMessages(){}

    public UserMessages(String name, String userTag, String message, Long userID) {
        this.name = name;
        this.userTag = userTag;
        this.message = message;
        this.userID = userID;
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

    public String getUserTag() {
        return userTag;
    }

    public void setUserTag(String userTag) {
        this.userTag = userTag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
}
