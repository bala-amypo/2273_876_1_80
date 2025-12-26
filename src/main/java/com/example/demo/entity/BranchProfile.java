package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BranchProfile {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String branchCode;
    private String branchName;
    private String contactEmail;
    private LocalDateTime lastSyncAt;
    private Boolean active;

    public BranchProfile() {}

    public BranchProfile(Long id, String code, String name, String email, LocalDateTime sync, Boolean active) {
        this.id = id;
        this.branchCode = code;
        this.branchName = name;
        this.contactEmail = email;
        this.lastSyncAt = sync;
        this.active = active;
    }

    @PrePersist
    public void prePersist() {
        if (lastSyncAt == null) lastSyncAt = LocalDateTime.now();
        if (active == null) active = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBranchCode() { return branchCode; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean a) { this.active = a; }
}
