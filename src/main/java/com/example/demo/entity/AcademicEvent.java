package com.example.demo.entity;

import com.example.demo.exception.ValidationException;
import jakarta.persistence.*;
import java.time.*;

@Entity
public class AcademicEvent {

    @Id @GeneratedValue
    private Long id;
    private Long branchId;
    private String title;
    private String eventType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String description;
    private LocalDateTime submittedAt;

    public AcademicEvent() {}

    public AcademicEvent(Long id, Long branchId, String title, String type,
                         LocalDate start, LocalDate end, String loc, String desc, LocalDateTime sub) {
        this.id = id;
        this.branchId = branchId;
        this.title = title;
        this.eventType = type;
        this.startDate = start;
        this.endDate = end;
        this.location = loc;
        this.description = desc;
        this.submittedAt = sub;
    }

    @PrePersist
    public void prePersist() {
        if (startDate.isAfter(endDate))
            throw new ValidationException("startDate cannot be after endDate");
        if (submittedAt == null) submittedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getBranchId() { return branchId; }
}
