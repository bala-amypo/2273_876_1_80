package com.example.demo.controller;

import com.example.demo.entity.AcademicEvent;
import com.example.demo.service.impl.AcademicEventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class AcademicEventController {

    @Autowired
    private AcademicEventServiceImpl academicEventService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CALENDAR_MANAGER')")
    public ResponseEntity<AcademicEvent> createEvent(@RequestBody AcademicEvent event) {
        return ResponseEntity.ok(academicEventService.createEvent(event));
    }

    @GetMapping("/branch/{branchId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('REVIEWER')")
    public ResponseEntity<List<AcademicEvent>> getEventsByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(academicEventService.getEventsByBranch(branchId));
    }
}
