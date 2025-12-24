package com.example.demo.controller;

import com.example.demo.entity.EventMergeRecord;
import com.example.demo.service.EventMergeService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/merge-records")
public class EventMergeController {

    private final EventMergeService eventMergeService;

    public EventMergeController(EventMergeService eventMergeService) {
        this.eventMergeService = eventMergeService;
    }

    /* --------------------------------------------------------
       1. POST /api/merge-records
       Body: eventIds + reason
    --------------------------------------------------------- */
    @PostMapping
public ResponseEntity<EventMergeRecord> mergeEvents(
        @RequestBody EventMergeRequest request) {

    EventMergeRecord mergeRecord =
            eventMergeService.mergeEvents(
                    request.getEventIds(),
                    request.getReason()
            );

    return ResponseEntity.ok(mergeRecord);
}


        // 2️⃣ Validate reason
        if (!request.containsKey("reason") || request.get("reason") == null) {
            return ResponseEntity.badRequest().body("reason is required");
        }

        String reason = request.get("reason").toString().trim();
        if (reason.isEmpty()) {
            return ResponseEntity.badRequest().body("reason cannot be blank");
        }

        // 3️⃣ Convert Integer -> Long
        List<Long> eventIds = eventIdsInt.stream()
                .map(Integer::longValue)
                .toList();

        // 4️⃣ Call service to insert data
        EventMergeRecord record = eventMergeService.mergeEvents(eventIds, reason);

        // 5️⃣ Return inserted record
        return ResponseEntity.ok(record);
    }

    /* --------------------------------------------------------
       2. GET /api/merge-records/{id}
    --------------------------------------------------------- */
    @GetMapping("/{id}")
    public ResponseEntity<EventMergeRecord> getMergeRecordById(@PathVariable Long id) {
        EventMergeRecord record = eventMergeService.getMergeRecordById(id);
        return ResponseEntity.ok(record);
    }

    /* --------------------------------------------------------
       3. GET /api/merge-records
    --------------------------------------------------------- */
    @GetMapping
    public ResponseEntity<List<EventMergeRecord>> getAllMergeRecords() {
        List<EventMergeRecord> records = eventMergeService.getAllMergeRecords();
        return ResponseEntity.ok(records);
    }

    /* --------------------------------------------------------
       4. GET /api/merge-records/range
       Query parameters: start, end (LocalDate)
    --------------------------------------------------------- */
    @GetMapping("/range")
    public ResponseEntity<List<EventMergeRecord>> getMergeRecordsByDateRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        List<EventMergeRecord> records = eventMergeService.getMergeRecordsByDate(start, end);
        return ResponseEntity.ok(records);
    }
}
