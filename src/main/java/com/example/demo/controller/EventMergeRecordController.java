package com.example.demo.controller;

import com.example.demo.dto.EventMergeRequest;
import com.example.demo.entity.EventMergeRecord;
import com.example.demo.service.EventMergeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/merge-records")
public class EventMergeController {

    private final EventMergeService eventMergeService;

    // 🔹 Constructor Injection
    public EventMergeController(EventMergeService eventMergeService) {
        this.eventMergeService = eventMergeService;
    }

    // 🔐 POST /api/merge-records
    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')") // JWT protected
    public EventMergeRecord mergeEvents(@RequestBody EventMergeRequest request) {
        return eventMergeService.mergeEvents(
                request.getEventIds(),
                request.getReason()
        );
    }

    // 🔐 GET /api/merge-records/{id}
    @GetMapping("/{id}")
    // @PreAuthorize("isAuthenticated()") // JWT protected
    public EventMergeRecord getMergeRecordById(@PathVariable Long id) {
        return eventMergeService.getMergeRecordById(id);
    }

    // 🔐 GET /api/merge-records
    @GetMapping
    // @PreAuthorize("isAuthenticated()") // JWT protected
    public List<EventMergeRecord> getAllMergeRecords() {
        return eventMergeService.getAllMergeRecords();
    }

    // 🔐 GET /api/merge-records/range?start=2025-01-01&end=2025-01-31
    @GetMapping("/range")
    // @PreAuthorize("isAuthenticated()") // JWT protected
    public List<EventMergeRecord> getMergeRecordsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,

            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end
    ) {
        return eventMergeService.getMergeRecordsByDate(start, end);
    }
}
