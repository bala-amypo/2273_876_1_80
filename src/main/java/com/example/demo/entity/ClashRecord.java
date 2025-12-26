package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clash_records")
public class ClashRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔑 REQUIRED FOR REPOSITORY QUERY
    @Column(nullable = false)
    private Long eventAId;

    @Column(nullable = false)
    private Long eventBId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean resolved = false;

    @Column(nullable = false)
    private LocalDateTime detectedAt;

    @Column
    private LocalDateTime resolvedAt;

    // =====================
    // Constructors
    // =====================

    public ClashRecord() {
    }

    public ClashRecord(Long eventAId, Long eventBId, String description) {
        this.eventAId = eventAId;
        this.eventBId = eventBId;
        this.description = description;
        this.detectedAt = LocalDateTime.now();
        this.resolved = false;
    }

    // =====================
    // Getters
    // =====================

    public Long getId() {
        return id;
    }

    public Long getEventAId() {
        return eventAId;
    }

    public Long getEventBId() {
        return eventBId;
    }

    public String getDescription() {
        return description;
    }

    public boolean isResolved() {
        return resolved;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    // =====================
    // Setters
    // =====================

    public void setId(Long id) {
        this.id = id;
    }

    public void setEventAId(Long eventAId) {
        this.eventAId = eventAId;
    }

    public void setEventBId(Long eventBId) {
        this.eventBId = eventBId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public void setDetectedAt(LocalDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }
}
