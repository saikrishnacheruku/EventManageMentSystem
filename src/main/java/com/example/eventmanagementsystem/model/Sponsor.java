package com.example.eventmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "sponsor")
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int sponsorId;

    @Column(name = "name")
    private String sponsorName;

    @Column(name = "industry")
    private String industry;

    @ManyToMany
    @JoinTable(name = "event_sponsor", joinColumns = @JoinColumn(name = "sponsorId"), inverseJoinColumns = @JoinColumn(name = "eventId"))
    @JsonIgnoreProperties("sponsors")
    private List<Event> events;

    // Constructors
    public Sponsor() {
    }

    public Sponsor(String name, String industry) {
        this.sponsorName = name;
        this.industry = industry;
    }

    // Getters and Setters
    public int getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(int id) {
        this.sponsorId = id;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String name) {
        this.sponsorName = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
