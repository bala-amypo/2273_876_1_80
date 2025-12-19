package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "harmonized_calendars")
public class HarmonizedCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "generated_by", nullable = false)
    private String generatedBy;

    @Column(name = "generated_at", updatable = false)
    private LocalDateTime generatedAt;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = false)
    private LocalDate effectiveTo;

    @Column(columnDefinition = "TEXT")
    private String eventsJson;

    /* -------------------- Constructors -------------------- */

    // No-arg constructor
    public HarmonizedCalendar() {
    }

    // Parameterized constructor
    public HarmonizedCalendar(Long id, String title, String generatedBy,
                              LocalDateTime generatedAt,
                              LocalDate effectiveFrom, LocalDate effectiveTo,
                              String eventsJson) {
        this.id = id;
        this.title = title;
        this.generatedBy = generatedBy;
        this.generatedAt = generatedAt;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.eventsJson = eventsJson;
    }

    /* -------------------- Lifecycle Hooks -------------------- */

    @PrePersist
    protected void onCreate() {
        this.generatedAt = LocalDateTime.now();

        if (effectiveFrom != null && effectiveTo != null &&
            effectiveFrom.isAfter(effectiveTo)) {
            throw new IllegalStateException(
                "effectiveFrom date must be less than or equal to effectiveTo date"
            );
        }
    }

    //-------------------- Getters & Setters -------------------- */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDate effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public String getEventsJson() {
        return eventsJson;
    }

    public void setEventsJson(String eventsJson) {
        this.eventsJson = eventsJson;
    }
}
