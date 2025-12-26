package com.example.demo.controller;

import com.example.demo.entity.EventMergeRecord;
import com.example.demo.service.impl.EventMergeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/merge-records")
public class EventMergeController {

    @Autowired
    private EventMergeServiceImpl eventMergeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CALENDAR_MANAGER')")
    public ResponseEntity<EventMergeRecord> mergeEvents(@RequestParam List<Long> eventIds,
                                                        @RequestParam String reason) {
        return ResponseEntity.ok(eventMergeService.mergeEvents(eventIds, reason));
    }

    @GetMapping("/range")
    @PreAuthorize("hasRole('ADMIN') or hasRole('REVIEWER')")
    public ResponseEntity<List<EventMergeRecord>> getMergeRecords(@RequestParam String start,
                                                                  @RequestParam String end) {
        return ResponseEntity.ok(eventMergeService.getMergeRecordsByDate(java.time.LocalDate.parse(start),
                java.time.LocalDate.parse(end)));
    }
}
