package com.example.SSK.model;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "counter", schema = "public")
public class CounterM {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int start, prices, certificate, timetable, club, arsenal, contactm;
    private String date;

    public CounterM() {
    }

    public CounterM(int start, int prices, int certificate, int timetable, int club, int arsenal, int contactm,String date) {
        this.start = start;
        this.prices = prices;
        this.certificate = certificate;
        this.timetable = timetable;
        this.club = club;
        this.arsenal = arsenal;
        this.contactm = contactm;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPrices() {
        return prices;
    }

    public void setPrices(int prices) {
        this.prices = prices;
    }

    public int getCertificate() {
        return certificate;
    }

    public void setCertificate(int certificate) {
        this.certificate = certificate;
    }

    public int getTimetable() {
        return timetable;
    }

    public void setTimetable(int timetable) {
        this.timetable = timetable;
    }

    public int getClub() {
        return club;
    }

    public void setClub(int club) {
        this.club = club;
    }

    public int getArsenal() {
        return arsenal;
    }

    public void setArsenal(int arsenal) {
        this.arsenal = arsenal;
    }

    public int getContactm() {
        return contactm;
    }

    public void setContactm(int contactm) {
        this.contactm = contactm;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
