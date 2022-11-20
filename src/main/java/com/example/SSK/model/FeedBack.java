package com.example.SSK.model;

import javax.persistence.*;

@Entity
@Table(name = "feedback", schema = "public")
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name, email, msg;

    public FeedBack() {
    }

    public FeedBack(String name, String email, String msg) {
        this.name = name;
        this.email = email;
        this.msg = msg;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
