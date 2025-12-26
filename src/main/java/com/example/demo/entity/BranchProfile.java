package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "branch_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BranchProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String branchCode;
    private String branchName;
    private String contactEmail;
    private LocalDateTime lastSyncAt;
    private Boolean active;

    @PrePersist
    public void prePersist() {
        if (active == null)
            active = true;
        lastSyncAt = LocalDateTime.now();
    }
}
