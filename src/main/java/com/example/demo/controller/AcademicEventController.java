package com.example.demo.controller;

import com.example.demo.entity.AcademicEvent;
import com.example.demo.service.AcademicEventService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class AcademicEventController {

    private final AcademicEventService academicEventService;

    public AcademicEventController(AcademicEventService academicEventService) {
        this.academicEventService = academicEventService;
    }

    @PostMapping
    public AcademicEvent create(@RequestBody AcademicEvent event) {
        return academicEventService.createEvent(event);
    }

    @PutMapping("/{id}")
    public AcademicEvent update(@PathVariable Long id,
                                @RequestBody AcademicEvent event) {
        return academicEventService.updateEvent(id, event);
    }

    @GetMapping("/branch/{branchId}")
    public Object getByBranch(@PathVariable Long branchId) {
        return academicEventService.getEventsByBranch(branchId);
    }

    @GetMapping("/{id}")
    public AcademicEvent getById(@PathVariable Long id) {
        return academicEventService.getEventById(id);
    }

    @GetMapping
    public Object getAll() {
        return academicEventService.getAllEvents();
    }
}
