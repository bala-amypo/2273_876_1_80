package com.example.demo.controller;

import com.example.demo.entity.AcademicEvent;
import com.example.demo.service.AcademicEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class AcademicEventController {

    private final AcademicEventService academicEventService;

    public AcademicEventController(AcademicEventService academicEventService) {
        this.academicEventService = academicEventService;
    }

    @PostMapping
    public ResponseEntity<?> submitEvent(@RequestBody AcademicEvent event) {
        return ResponseEntity.ok(academicEventService.submitEvent(event));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<?> getByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(
                academicEventService.getEventsByBranch(branchId)
        );
    }
}
