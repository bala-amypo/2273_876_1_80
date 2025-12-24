package com.example.demo.controller;

import com.example.demo.dto.EventMergeRequest;
import com.example.demo.entity.EventMergeRecord;
import com.example.demo.service.EventMergeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/merge-records")
public class EventMergeController {

    private final EventMergeService eventMergeService;

    public EventMergeController(EventMergeService eventMergeService) {
        this.eventMergeService = eventMergeService;
    }

    /* --------------------------------------------------------
       POST /api/merge-records
       Body: EventMergeRequest
    --------------------------------------------------------- */
    @PostMapping
    public ResponseEntity<EventMergeRecord> mergeEvents(
            @Valid @RequestBody EventMergeRequest request) {

        EventMergeRecord mergeRecord =
                eventMergeService.mergeEvents(request.getEventIds(), request.getReason());

        return ResponseEntity.ok(mergeRecord);
    }

    /* --------------------------------------------------------
       GET /api/merge-records/{id}
    --------------------------------------------------------- */
    @GetMapping("/{id}")
    public ResponseEntity<EventMergeRecord> getMergeRecordById(@PathVariable Long id) {
        EventMergeRecord mergeRecord = eventMergeService.getMergeRecordById(id);
        return ResponseEntity.ok(mergeRecord);
    }

    /* --------------------------------------------------------
       GET /api/merge-records
    --------------------------------------------------------- */
    @GetMapping
    public ResponseEntity<List<EventMergeRecord>> getAllMergeRecords() {
        return ResponseEntity.ok(eventMergeService.getAllMergeRecords());
    }

    /* --------------------------------------------------------
       GET /api/merge-records/range
    --------------------------------------------------------- */
    @GetMapping("/range")
    public ResponseEntity<List<EventMergeRecord>> getMergeRecordsByDateRange(
            @RequestParam("start") LocalDate start,
            @RequestParam("end") LocalDate end) {

        List<EventMergeRecord> records = eventMergeService.getMergeRecordsByDate(start, end);
        return ResponseEntity.ok(records);
    }
}
