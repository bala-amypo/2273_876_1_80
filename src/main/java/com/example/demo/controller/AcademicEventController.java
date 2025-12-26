package com.example.demo.controller;

import com.example.demo.entity.AcademicEvent;
import com.example.demo.service.AcademicEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Academic Events")
public class AcademicEventController {
    
    private final AcademicEventService academicEventService;
    
    public AcademicEventController(AcademicEventService academicEventService) {
        this.academicEventService = academicEventService;
    }
    
    @PostMapping
    @Operation(summary = "Create academic event")
    public ResponseEntity<AcademicEvent> createEvent(@RequestBody AcademicEvent event) {
        return ResponseEntity.ok(academicEventService.createEvent(event));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update academic event")
    public ResponseEntity<AcademicEvent> updateEvent(@PathVariable Long id, @RequestBody AcademicEvent event) {
        return ResponseEntity.ok(academicEventService.updateEvent(id, event));
    }
    
    @GetMapping("/branch/{branchId}")
    @Operation(summary = "Get events by branch")
    public ResponseEntity<List<AcademicEvent>> getEventsByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(academicEventService.getEventsByBranch(branchId));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get event by ID")
    public ResponseEntity<AcademicEvent> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(academicEventService.getEventById(id));
    }
    
    @GetMapping
    @Operation(summary = "Get all events")
    public ResponseEntity<List<AcademicEvent>> getAllEvents() {
        return ResponseEntity.ok(academicEventService.getAllEvents());
    }
}