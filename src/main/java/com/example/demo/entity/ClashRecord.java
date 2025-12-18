package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clash_record")
public class ClashRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_a_id", nullable = false)
    private Long eventAId;

    @Column(name = "event_b_id", nullable = false)
    private Long eventBId;

    @Column(name = "clash_type")
    private String clashType;

    private String severity;

    @Column(length = 1000)
    private String details;

    @Column(name = "detected_at", updatable = false)
    private LocalDateTime detectedAt;

    @Column(nullable = false)
    private Boolean resolved = false;

    // Auto-generate detectedAt
    @PrePersist
    public void prePersist() {
        this.detectedAt = LocalDateTime.now();
        if (this.resolved == null) {
            this.resolved = false;
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Long getEventAId() {
        return eventAId;
    }

    public void setEventAId(Long eventAId) {
        this.eventAId = eventAId;
    }

    public Long getEventBId() {
        return eventBId;
    }

    public void setEventBId(Long eventBId) {
        this.eventBId = eventBId;
    }

    public String getClashType() {
        return clashType;
    }

    public void setClashType(String clashType) {
        this.clashType = clashType;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }

    public Boolean getResolved() {
        return resolved;
    }

    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }
}
