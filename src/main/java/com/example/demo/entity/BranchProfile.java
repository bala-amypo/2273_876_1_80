package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "branch_profile",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "branch_code")
    }
)
public class BranchProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_code", nullable = false, unique = true)
    private String branchCode;

    @Column(name = "branch_name", nullable = false)
    private String branchName;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "last_sync_at", updatable = false)
    private LocalDateTime lastSyncAt;

    @Column(nullable = false)
    private Boolean active = true;

    // Auto-generate lastSyncAt
    @PrePersist
    public void prePersist() {
        this.lastSyncAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public LocalDateTime getLastSyncAt() {
        return lastSyncAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    public BranchProfile(Long id,String branchCode,String branchName,String contactEmail,LocalDateTime lastSyncAt,Boolean active){
    this.id=id;
    this.branchCode=branchCode;
    this.branchName=branchName;
    this.contactEmail=contactEmail;
    this.lastSyncAt=lastSyncAt;
    this.active=active;

}
}
