package com.example.demo.controller;

import com.example.demo.entity.AcademicEvent;
import com.example.demo.service.AcademicEventService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class AcademicEventController {

    private final AcademicEventService academicEventService;

    public AcademicEventController(AcademicEventService academicEventService) {
        this.academicEventService = academicEventService;
    }

    // POST /api/events
    @PostMapping
    public ResponseEntity<AcademicEvent> createEvent(
            @RequestBody AcademicEvent event) {
        return ResponseEntity.ok(academicEventService.createEvent(event));
    }

    // PUT /api/events/{id}
    @PutMapping("/{id}")
    public ResponseEntity<AcademicEvent> updateEvent(
            @PathVariable Long id,
            @RequestBody AcademicEvent event) {
        return ResponseEntity.ok(academicEventService.updateEvent(id, event));
    }

    // GET /api/events/branch/{branchId}
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<AcademicEvent>> getEventsByBranch(
            @PathVariable Long branchId) {
        return ResponseEntity.ok(
                academicEventService.getEventsByBranch(branchId));
    }

    // GET /api/events/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AcademicEvent> getEventById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                academicEventService.getEventById(id));
    }

    // GET /api/events
    @GetMapping
    public ResponseEntity<List<AcademicEvent>> getAllEvents() {
        return ResponseEntity.ok(
                academicEventService.getAllEvents());
    }
}
