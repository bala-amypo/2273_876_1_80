package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clash_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClashRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventAId;
    private Long eventBId;
    private String clashType;
    private String severity;
    private String details;
    private LocalDateTime detectedAt;
    private Boolean resolved;

    @PrePersist
    public void prePersist() {
        detectedAt = LocalDateTime.now();
        if (resolved == null)
            resolved = false;
    }
}
