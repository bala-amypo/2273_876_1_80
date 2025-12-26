package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class EventMergeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<Long> eventIds;

    private String reason;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public List<Long> getEventIds() { return eventIds; }
    public void setEventIds(List<Long> eventIds) { this.eventIds = eventIds; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
