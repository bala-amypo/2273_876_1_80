package com.example.demo.controller;


import com.example.demo.dto.EventMergeRequest;
import com.example.demo.entity.EventMergeRecord;
import com.example.demo.service.EventMergeRecordService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/merge-records")
public class EventMergeRecordController {

    private final EventMergeRecordService eventMergeRecordService;

    public EventMergeRecordController(EventMergeRecordService eventMergeRecordService) {
        this.eventMergeRecordService = eventMergeRecordService;
    }
    // 🔐 POST /api/merge-records
    @PostMapping
    public EventMergeRecord mergeEvents(@RequestBody EventMergeRequest request) {
        return eventMergeRecordService.mergeEvents(
                request.getEventIds(),
                request.getReason()
        );
    }

    // 🔐 GET /api/merge-records/{id}
    @GetMapping("/{id}")
    public EventMergeRecord getMergeRecordById(@PathVariable Long id) {
        return eventMergeRecordService.getMergeRecordById(id);
    }

    // 🔐 GET /api/merge-records
    @GetMapping
    public List<EventMergeRecord> getAllMergeRecords() {
        return eventMergeRecordService.getAllMergeRecords();
    }

    // 🔐 GET /api/merge-records/range
    @GetMapping("/range")
    public List<EventMergeRecord> getMergeRecordsByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end
    ) {
        return eventMergeRecordService.getMergeRecordsByDate(start, end);
    }
}
