package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clash_records")
public class ClashRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean resolved = false;

    @Column
    private LocalDateTime detectedAt;

    @Column
    private LocalDateTime resolvedAt;

    // =====================
    // Constructors
    // =====================

    public ClashRecord() {
    }

    public ClashRecord(String description) {
        this.description = description;
        this.detectedAt = LocalDateTime.now();
        this.resolved = false;
    }

    // =====================
    // Getters & Setters
    // =====================

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
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
