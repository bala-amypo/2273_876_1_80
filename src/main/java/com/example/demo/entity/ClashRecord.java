package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class ClashRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long event1Id;
    private Long event2Id;

    private String reason;

    // Constructors
    public ClashRecord() {}
    public ClashRecord(Long event1Id, Long event2Id, String reason) {
        this.event1Id = event1Id;
        this.event2Id = event2Id;
        this.reason = reason;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEvent1Id() { return event1Id; }
    public void setEvent1Id(Long event1Id) { this.event1Id = event1Id; }
    public Long getEvent2Id() { return event2Id; }
    public void setEvent2Id(Long event2Id) { this.event2Id = event2Id; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
