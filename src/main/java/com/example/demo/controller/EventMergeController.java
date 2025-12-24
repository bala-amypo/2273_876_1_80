package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.EventMergeRecord;
import com.example.demo.service.EventMergeService;

@RestController
@RequestMapping("/api/merge-records")
public class EventMergeController {

    private final EventMergeService eventMergeService;

    /* ---------------- Constructor Injection ---------------- */

    public EventMergeController(EventMergeService eventMergeService) {
        this.eventMergeService = eventMergeService;
    }

    /* --------------------------------------------------------
       1. POST /api/merge-records
       Body: eventIds + reason
       Access: Protected by JWT
    --------------------------------------------------------- */

    @PostMapping
    public ResponseEntity<EventMergeRecord> mergeEvents(
            @RequestBody Map<String, Object> request) {

        // Extract eventIds (JSON numbers → Integer)
        @SuppressWarnings("unchecked")
        List<Integer> eventIdsInt =
                (List<Integer>) request.get("eventIds");

        String reason = (String) request.get("reason");

        // Convert Integer → Long
        List<Long> eventIds = eventIdsInt.stream()
                .map(Integer::longValue)
                .toList();

        EventMergeRecord record =
                eventMergeService.mergeEvents(eventIds, reason);

        return ResponseEntity.ok(record);
    }

    /* --------------------------------------------------------
       2. GET /api/merge-records/{id}
       Access: Protected by JWT
    --------------------------------------------------------- */

    @GetMapping("/{id}")
    public ResponseEntity<EventMergeRecord> getMergeRecordById(
            @PathVariable Long id) {

        EventMergeRecord record =
                eventMergeService.getMergeRecordById(id);

        return ResponseEntity.ok(record);
    }

    /* --------------------------------------------------------
       3. GET /api/merge-records
       Access: Protected by JWT
    --------------------------------------------------------- */

    @GetMapping
    public ResponseEntity<List<EventMergeRecord>> getAllMergeRecords() {
        return ResponseEntity.ok(
                eventMergeService.getAllMergeRecords()
        );
    }

    /* --------------------------------------------------------
       4. GET /api/merge-records/range
       Query Params: start, end (LocalDate)
       Access: Protected by JWT
    --------------------------------------------------------- */

    @GetMapping("/range")
    public ResponseEntity<List<EventMergeRecord>> getMergeRecordsByDateRange(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,

            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end) {

        List<EventMergeRecord> records =
                eventMergeService.getMergeRecordsByDate(start, end);

        return ResponseEntity.ok(records);
    }
}
