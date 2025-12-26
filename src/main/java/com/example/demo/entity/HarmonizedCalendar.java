package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class HarmonizedCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String generatedBy;

    // Constructors
    public HarmonizedCalendar() {}
    public HarmonizedCalendar(String title, String generatedBy) {
        this.title = title;
        this.generatedBy = generatedBy;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGeneratedBy() { return generatedBy; }
    public void setGeneratedBy(String generatedBy) { this.generatedBy = generatedBy; }
}
