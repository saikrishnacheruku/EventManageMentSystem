package com.example.eventmanagementsystem.model;

import javax.persistence.*;
import com.example.eventmanagementsystem.model.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int eventId;

    @Column(name = "name")
    private String eventName;

    @Column(name = "date")
    private String date;

    @ManyToMany(mappedBy = "events")
    @JsonIgnoreProperties("events")

    private List<Sponsor> sponsors;

    // Constructors
    public Event() {
    }

    public Event(String name, String date) {
        this.eventName = name;
        this.date = date;

    }

    // Getters and Setters
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int id) {
        this.eventId = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String name) {
        this.eventName = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }
}
