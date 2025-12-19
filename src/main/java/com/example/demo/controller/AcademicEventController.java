package com.example.demo.controller;

import com.example.demo.entity.AcademicEvent;
import com.example.demo.service.AcademicEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class AcademicEventController {

    @Autowired
    private AcademicEventService academicEventService;

    // 1️⃣ POST /api/events
    // Create academic event
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AcademicEvent> createEvent(
            @RequestBody AcademicEvent event) {

        AcademicEvent createdEvent =
                academicEventService.createEvent(event);
        return ResponseEntity.ok(createdEvent);
    }

    // 2️⃣ PUT /api/events/{id}
    // Update academic event
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AcademicEvent> updateEvent(
            @PathVariable Long id,
            @RequestBody AcademicEvent event) {

        AcademicEvent updatedEvent =
                academicEventService.updateEvent(id, event);
        return ResponseEntity.ok(updatedEvent);
    }

    // 3️⃣ GET /api/events/branch/{branchId}
    // Get events by branch
    @GetMapping("/branch/{branchId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<AcademicEvent>> getEventsByBranch(
            @PathVariable Long branchId) {

        List<AcademicEvent> events =
                academicEventService.getEventsByBranch(branchId);
        return ResponseEntity.ok(events);
    }

    // 4️⃣ GET /api/events/{id}
    // Get event by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AcademicEvent> getEventById(
            @PathVariable Long id) {

        AcademicEvent event =
                academicEventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    // 5️⃣ GET /api/events
    // Get all events
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<AcademicEvent>> getAllEvents() {

        List<AcademicEvent> events =
                academicEventService.getAllEvents();
        return ResponseEntity.ok(events);
    }
}
