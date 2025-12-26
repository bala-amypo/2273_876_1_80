package com.example.demo.controller;

import com.example.demo.entity.AcademicEvent;
import com.example.demo.service.AcademicEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Academic Events")
public class AcademicEventController {

    private final AcademicEventService service;

    public AcademicEventController(AcademicEventService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create event")
    public AcademicEvent create(@RequestBody AcademicEvent e) {
        return service.createEvent(e);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update event")
    public AcademicEvent update(@PathVariable Long id,
                                @RequestBody AcademicEvent e) {
        return service.updateEvent(id, e);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get event by ID")
    public AcademicEvent get(@PathVariable Long id) {
        return service.getEventById(id);
    }

    @GetMapping
    @Operation(summary = "List all events")
    public List<AcademicEvent> all() {
        return service.getAllEvents();
    }

    @GetMapping("/branch/{branchId}")
    @Operation(summary = "Get events by branch")
    public List<AcademicEvent> branch(@PathVariable Long branchId) {
        return service.getEventsByBranch(branchId);
    }
}
