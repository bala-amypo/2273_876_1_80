package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_merge_record")
public class EventMergeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_event_ids", nullable = false)
    private String sourceEventIds; // comma-separated IDs

    @Column(name = "merged_title", nullable = false)
    private String mergedTitle;

    @Column(name = "merged_start_date", nullable = false)
    private LocalDate mergedStartDate;

    @Column(name = "merged_end_date", nullable = false)
    private LocalDate mergedEndDate;

    @Column(name = "merge_reason")
    private String mergeReason;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Auto-generate createdAt
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getSourceEventIds() {
        return sourceEventIds;
    }

    public void setSourceEventIds(String sourceEventIds) {
        this.sourceEventIds = sourceEventIds;
    }

    public String getMergedTitle() {
        return mergedTitle;
    }

    public void setMergedTitle(String mergedTitle) {
        this.mergedTitle = mergedTitle;
    }

    public LocalDate getMergedStartDate() {
        return mergedStartDate;
    }

    public void setMergedStartDate(LocalDate mergedStartDate) {
        this.mergedStartDate = mergedStartDate;
    }

    public LocalDate getMergedEndDate() {
        return mergedEndDate;
    }

    public void setMergedEndDate(LocalDate mergedEndDate) {
        this.mergedEndDate = mergedEndDate;
    }

    public String getMergeReason() {
        return mergeReason;
    }

    public void setMergeReason(String mergeReason) {
        this.mergeReason = mergeReason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
